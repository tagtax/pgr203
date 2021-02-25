package com.pgr203.k9001.http;

import java.io.IOException;
import java.sql.SQLException;

public interface HttpHandle {
    void handle(HttpRequest request, HttpResponse response) throws IOException, SQLException;
}
