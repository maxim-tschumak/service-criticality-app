package org.component.criticality.metrics.utils;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DataSourceTestConfiguration {

    @Bean
    public DataSource dataSource() throws IOException {
        return embeddedPostgres().getPostgresDatabase();
    }

    @Bean
    public EmbeddedPostgreSQL embeddedPostgres() throws IOException {
        return EmbeddedPostgreSQL.start();
    }
}
