package com.ahasan.time_sheet_mngmnt_sys.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
    public OpenAPI openAPI() {

        Server localServer = new Server();
        localServer.setUrl(localUrl);
        localServer.setDescription("Local Environment");

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Environment");

        Server qaServer = new Server();
        qaServer.setUrl(qaUrl);
        qaServer.setDescription("QA Environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production Environment");

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Time Sheet Management System APIs")
                                .description("Employee and Manager REST APIs Documentation")
                                .version("1.0.0")
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
                                localServer,
                                devServer,
                                qaServer,
                                prodServer
                        )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project Documentation")
                );
    }
}