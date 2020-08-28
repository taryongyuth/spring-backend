package com.springboot.rest.backend.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Auth", "users related"), new Tag("API", "apis related"))
                .groupName("Api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.rest.backend"))
                .paths(paths())
                .build()
                .apiInfo(metaData())
                .securitySchemes(Arrays.asList(apiKey()));
    }

//    @Bean
//    public Docket swaggerUserApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("User")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.springboot.rest.backend.config"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(metaData())
//                .securitySchemes(Arrays.asList(apiKey()));
//    }

    private ApiKey apiKey() {
        return new ApiKey("Token", "Authorization", "header");
    }

    private Predicate<String> paths() {
        return Predicates.and(PathSelectors.any(), Predicates.not(PathSelectors.regex("/error.*")));
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "A simple Spring Boot application with REST API services provider.",
                "0.0.1",
                "",
                "Yongyuth Jantrachat",
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}