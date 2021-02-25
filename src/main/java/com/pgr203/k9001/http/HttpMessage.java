package com.pgr203.k9001.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    static String readLine(InputStream inputStream) throws IOException {
        StringBuilder requestBuilder = new StringBuilder();
        int requestLine;
        while ((requestLine = inputStream.read()) != -1) {
            if (requestLine == '\r') break;
            requestBuilder.append((char) requestLine);
        }
        return requestBuilder.toString();
    }

    static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()) {
            String[] header = headerLine.split(":", 2);
            headers.put(header[0].trim().toLowerCase(), header[1].trim());
        }
        return headers;
    }

    static String readBody(Map<String, String> headers, InputStream inputStream) throws IOException {
        if (headers.containsKey("content-length")) {
            StringBuilder body = new StringBuilder();
            for (int currentLength = 0; currentLength < Integer.parseInt(headers.get("content-length")); currentLength++) {
                body.append((char) inputStream.read());
            }
            return body.toString();
        } else {
            return "";
        }
    }
}
