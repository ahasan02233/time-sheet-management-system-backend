package com.ahasan.time_sheet_mngmnt_sys.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${app.server.local}")
    private String localUrl;

    @Value("${app.server.dev}")
    private String devUrl;

    @Value("${app.server.qa}")
    private String qaUrl;

    @Value("${app.server.prod}")
    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()

                .info(
                        new Info()
                                .title("Time Sheet Management System API")
                                .version("1.0")
                                .description(
                                        "Employee, Manager, Timesheet and Leave Management APIs"
                                )
                                .contact(
                                        new Contact()
                                                .name("Ibala Hasan")
                                                .email("admin@test.com")
                                )
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                )
                )

                .servers(
                        List.of(
                                new Server()
                                        .url(localUrl)
                                        .description("Local Environment"),

                                new Server()
                                        .url(devUrl)
                                        .description("Development Environment"),

                                new Server()
                                        .url(qaUrl)
                                        .description("QA Environment"),

                                new Server()
                                        .url(prodUrl)
                                        .description("Production Environment")
                        )
                )

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project Documentation")
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )

                .schemaRequirement(
                        securitySchemeName,

                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }
}