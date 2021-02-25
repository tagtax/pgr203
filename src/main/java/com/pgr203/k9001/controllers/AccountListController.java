package com.pgr203.k9001.controllers;

import com.pgr203.k9001.http.HttpHandle;
import com.pgr203.k9001.http.HttpRequest;
import com.pgr203.k9001.http.HttpResponse;
import com.pgr203.k9001.service.Accounts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class AccountListController implements HttpHandle {
    private final Accounts accounts;

    public AccountListController(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getMethod().equals("GET")) {
                String accountList = accounts.readAll().stream()
                        .map(account -> String.format(
                                "<option value='%s'>%s %s</option>",
                                account.getId(),
                                escapeHtml4(account.getAccountName()),
                                escapeHtml4("<" + account.getEmail() + ">"))
                        )
                        .collect(Collectors.joining(""));

                response.setStatusCode(200);
                response.write(accountList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.setStatusCode(500);
            response.write("Internal Server Error");
        }
    }
}
