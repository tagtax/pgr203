package com.pgr203.k9001.http;

import com.pgr203.k9001.http.handlers.NotFoundHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

public class HttpServer {
    private final ServerSocket serverSocket;
    private final Map<String, HttpHandle> handlers = new HashMap<>();
    private HttpHandle defaultHandler;

    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.defaultHandler = new NotFoundHandler();
    }

    public void listenAndServe() {
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                String requestString = HttpMessage.readLine(socket.getInputStream());
                String[] requestLines = requestString.split("\r\n");
                String[] startLine = requestLines[0].split(" ");
                String method = startLine[0];
                String path = startLine[1];
                String[] splitPath;
                Map<String, List<String>> query = new HashMap<>();
                String queryString = "";
                String body = "";

                if ((splitPath = path.split("\\?", 2)).length > 1) {
                    path = splitPath[0];
                    queryString = splitPath[1];
                }
                if (queryString != null && !queryString.isBlank()) {
                    query = parseQueryParams(queryString);
                }
                Map<String, String> headers = HttpMessage.readHeaders(socket.getInputStream());
                if (headers.containsKey("content-length")) {
                    body = HttpMessage.readBody(headers, socket.getInputStream());
                }

                HttpRequest request = new HttpRequest(method, path, headers, query, body);
                HttpResponse response = new HttpResponse(socket.getOutputStream());
                handlers.getOrDefault(path, defaultHandler).handle(request, response);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefaultHandler(HttpHandle handler) {
        this.defaultHandler = handler;
    }

    public void handle(String path, HttpHandle handle) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        handlers.put(path, handle);
    }

    private Map<String, List<String>> parseQueryParams(String queryString) {
        queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8);

        Map<String, List<String>> parameters = new HashMap<>();
        for (String query : queryString.split("&")) {
            String[] parameter = query.split("=");

            String parameterName = parameter[0].trim();
            String parameterValue = parameter[1].trim();

            if (parameters.containsKey(parameterName)) {
                parameters.get(parameterName).add(parameterValue);
            } else {
                List<String> parameterValues = new ArrayList<>();
                parameterValues.add(parameterValue);
                parameters.put(parameterName, parameterValues);
            }
        }
        return parameters;
    }

    public static Map<String, String> parseQueryString(String body) {
        body = URLDecoder.decode(body, StandardCharsets.UTF_8);

        Map<String, String> parameters = new HashMap<>();
        for (String query : body.split("&")) {
            String[] parameter = query.split("=");

            String parameterName = parameter[0].trim();
            String parameterValue = parameter[1].trim();

            parameters.put(parameterName, parameterValue);
        }
        return parameters;
    }
}
