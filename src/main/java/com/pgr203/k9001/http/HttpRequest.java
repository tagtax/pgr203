package com.pgr203.k9001.http;

import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, List<String>> query;
    private final String body;

    public HttpRequest(String method, String path, Map<String, String> headers, Map<String, List<String>> query, String body) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.query = query;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, List<String>> getQuery() {
        return query;
    }

    public String getBody() {
        return body;
    }
}
