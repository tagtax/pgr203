package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.services.Projects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class TaskListController implements HttpHandle {
    private final Projects projects;

    public TaskListController(Projects projects) {
        this.projects = projects;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("GET")) {
                long accountId = Integer.parseInt(request.getQuery().get("id").get(0));
                String projectList = projects.getTaskList(accountId).stream()
                        .map(project -> String.format(
                                "<li>%s %s</li>",
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
