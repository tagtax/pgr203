package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.http.HttpServer;
import com.pgr203.k9001.services.Projects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class JoinProjectController implements HttpHandle {
    private final Projects projects;

    public JoinProjectController(Projects projects) {
        this.projects = projects;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("POST")) {
                Map<String, String> query = HttpServer.parseQueryString(request.getBody());
                if (query.containsKey("member_id") && query.containsKey("project_id")) {
                    long accountId = Long.parseLong(query.get("member_id"));
                    long projectId = Long.parseLong(query.get("project_id"));

                    projects.joinProject(accountId, projectId);
                }
                response.redirectToGet(request.getHeaders().get("referer"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.setStatusCode(500);
            response.write("Internal Server Error");
        }
    }
}
