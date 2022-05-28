package com.angelperez.iobuildersusers.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource("jdbc:postgresql://localhost:5432/postgres?currentSchema=users", "postgres", "postgres")
            .locations("classpath:scripts/migration"));
    }

    @Bean
    public PostgresqlConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .schema("users")
                .host("127.0.0.1")
                .port(5432)
                .database("postgres")
                .username("postgres")
                .password("postgres")
                .build());
    }
}

