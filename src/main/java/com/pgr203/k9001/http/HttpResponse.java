package com.pgr203.k9001.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final OutputStream outputStream;
    private int statusCode;
    private final Map<String, String> headers = new HashMap<>();
    private String body = "";

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.statusCode = 404;
        this.headers.put("content-type", "text/plain");
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void notFound() throws IOException {
        setStatusCode(404);
        write();
    }

    public void notFound(String body) throws IOException {
        setStatusCode(404);
        write(body);
    }

    public void redirect(String path) throws IOException {
        setStatusCode(302);
        setHeader("location", path);
        write();
    }

    public void write() throws IOException {
        writeResponse();
    }

    public void write(String body) throws IOException {
        this.body = body;
        writeResponse();
    }

    private void writeResponse() throws IOException {
        if (!body.isBlank()) setHeader("content-length", Integer.toString(body.getBytes().length));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ").append(statusCode).append("\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            stringBuilder.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        if (!body.isBlank()) stringBuilder.append("\r\n").append(body);
        outputStream.write(stringBuilder.toString().getBytes());
        outputStream.close();
    }
}
