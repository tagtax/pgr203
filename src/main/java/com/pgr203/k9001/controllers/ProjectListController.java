package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.service.Projects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class ProjectListController implements HttpHandle {
    private final Projects projects;

    public ProjectListController(Projects projects) {
        this.projects = projects;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("GET")) {
                String projectList = projects.readAll().stream()
                        .map(project -> String.format(
                                "<option value='%s'>%s %s</option>",
                                project.getId(),
                                escapeHtml4(project.getProjectName()),
                                project.getStatus() ? "✅" : "🚫"
                                ))
                        .collect(Collectors.joining(""));

                response.setStatusCode(200);
                response.write(projectList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.setStatusCode(500);
            response.write("Internal Server Error");
        }
    }
}
