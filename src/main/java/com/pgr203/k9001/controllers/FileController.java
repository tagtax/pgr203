package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileController implements HttpHandle {
    private static final Map<String, String> MIME_MAP = new HashMap<>();

    static {
        MIME_MAP.put("appcache", "text/cache-manifest");
        MIME_MAP.put("css", "text/css");
        MIME_MAP.put("gif", "image/gif");
        MIME_MAP.put("html", "text/html");
        MIME_MAP.put("js", "application/javascript");
        MIME_MAP.put("json", "application/json");
        MIME_MAP.put("jpg", "image/jpeg");
        MIME_MAP.put("jpeg", "image/jpeg");
        MIME_MAP.put("mp4", "video/mp4");
        MIME_MAP.put("pdf", "application/pdf");
        MIME_MAP.put("png", "image/png");
        MIME_MAP.put("svg", "image/svg+xml");
        MIME_MAP.put("xlsm", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_MAP.put("xml", "application/xml");
        MIME_MAP.put("zip", "application/zip");
        MIME_MAP.put("md", "text/plain");
        MIME_MAP.put("txt", "text/plain");
        MIME_MAP.put("php", "text/plain");
    }

    private String assetRoot;

    public FileController(String assetRoot) {
        this.assetRoot = assetRoot;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        File file = new File(assetRoot + request.getPath());
        if (file.isDirectory()) {
            file = new File(file, "index.html");
        }

        if (file.exists()) {
            for (Map.Entry<String, String> mime : MIME_MAP.entrySet()) {
                if (file.getPath().endsWith(mime.getKey())) {
                    response.setHeader("content-type", mime.getValue());
                    break;
                }
            }
            response.setStatusCode(200);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            StringBuilder fileBuilder = new StringBuilder();
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                fileBuilder.append(fileLine).append("\r\n");
            }

            response.write(fileBuilder.toString());
        } else {
            response.notFound();
        }
    }
}
