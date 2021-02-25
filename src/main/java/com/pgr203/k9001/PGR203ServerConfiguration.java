package com.pgr203.k9001;

import com.pgr203.k9001.configuration.*;

public class PGR203ServerConfiguration {
    private final DataSourceConfiguration dataSource = new DataSourceConfiguration();

    public DataSourceConfiguration getDataSourceConfiguration() {
        return dataSource;
    }
}
