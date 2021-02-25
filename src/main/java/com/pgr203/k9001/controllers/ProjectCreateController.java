package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.http.HttpServer;
import com.pgr203.k9001.model.Project;
import com.pgr203.k9001.service.Projects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ProjectCreateController implements HttpHandle {
    private final Projects projects;

    public ProjectCreateController(Projects projects) {
        this.projects = projects;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("POST")) {
                Map<String, String> query = HttpServer.parseQueryString(request.getBody());

                Project project = new Project();
                project.setProjectName(query.get("project_name"));
                projects.create(project);

                response.redirect("/");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.setStatusCode(500);
            response.write("Internal Server Error");
        }
    }
}
