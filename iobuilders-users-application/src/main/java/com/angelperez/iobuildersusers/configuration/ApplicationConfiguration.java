package com.angelperez.iobuildersusers.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Docket apiConfiguration() {
        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)
                .and(RequestHandlerSelectors.basePackage("org.springframework").negate()))
            .paths(PathSelectors.any())
            .build();
    }
}
