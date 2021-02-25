package com.pgr203.k9001.configuration;

public class DataSourceConfiguration {
    public String getUrl() {
        return System.getProperty("dataSource.url");
    }

    public String getUsername() {
        return System.getProperty("dataSource.username");
    }

    public String getPassword() {
        return System.getProperty("dataSource.password");
    }
}
