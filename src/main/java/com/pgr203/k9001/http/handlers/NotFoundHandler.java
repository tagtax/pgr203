package com.pgr203.k9001.http.handlers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;

import java.io.IOException;
import java.sql.SQLException;

public class NotFoundHandler implements HttpHandle {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, SQLException {
        response.notFound();
    }
}
