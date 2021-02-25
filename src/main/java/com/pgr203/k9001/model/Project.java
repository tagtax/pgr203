package com.pgr203.k9001.model;

import javax.security.auth.Subject;
import java.security.Principal;

public class Project implements Principal {
    private long id;

    private String name;

    private boolean status;

    public Project() {
    }

    public Project(long id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return name;
    }

    public void setProjectName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
