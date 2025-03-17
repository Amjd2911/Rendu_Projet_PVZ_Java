// src/test/java/com/epf/config/TestDatabaseConfig.java
package com.epf.config;

import com.epf.dao.impl.MapDaoImpl;
import com.epf.dao.impl.PlanteDaoImpl;
import com.epf.dao.impl.ZombieDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@Profile("test")
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema-h2.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public MapDaoImpl mapDao(JdbcTemplate jdbcTemplate) {
        return new MapDaoImpl(jdbcTemplate);
    }

    @Bean
    public ZombieDaoImpl zombieDao(JdbcTemplate jdbcTemplate) {
        return new ZombieDaoImpl(jdbcTemplate);
    }

    @Bean
    public PlanteDaoImpl planteDao(JdbcTemplate jdbcTemplate) {
        return new PlanteDaoImpl(jdbcTemplate);
    }

    private String readResourceFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}