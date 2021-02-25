package com.pgr203.k9001.model;

import javax.security.auth.Subject;
import java.security.Principal;

public class Account implements Principal {
    private long id;

    private String name;

    private String email;

    public Account() {
    }

    public Account(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return name;
    }

    public void setAccountName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
