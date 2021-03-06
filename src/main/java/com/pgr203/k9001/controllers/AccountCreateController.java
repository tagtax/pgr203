package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.http.HttpServer;
import com.pgr203.k9001.services.Accounts;
import com.pgr203.k9001.model.Account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AccountCreateController implements HttpHandle {
    private final Accounts accounts;

    public AccountCreateController(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("POST")) {
                Map<String, String> query = HttpServer.parseQueryString(request.getBody());
                if (query.containsKey("full_name") && query.containsKey("email")) {
                    Account account = new Account();
                    account.setAccountName(query.get("full_name"));
                    account.setEmail(query.get("email"));

                    accounts.create(account);
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
