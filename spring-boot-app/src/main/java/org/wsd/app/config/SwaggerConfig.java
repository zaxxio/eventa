package org.wsd.app.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
        @Server(url = "http://localhost:8080"),
        @Server(url = "http://avaand.com:8080")
})
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .description("Eventa is infrastructure ready event-sourcing and (Command Query Responsibility Segregation) CQRS Framework.\nThis library provides a robust infrastructure for implementing the Command Query Responsibility Segregation (CQRS) pattern along with Event Sourcing in Java applications. CQRS separates the responsibility of handling commands (write operations) from queries (read operations), while Event Sourcing ensures that changes to the application state are captured as a sequence of events.\n" +
                                "\n ")
                        .contact(new Contact()
                                .name("Partha Sutradhar")
                                .email("partharaj.dev@gmail.com")
                                .url("https://linkedin.com/in/partha-sutradhar")
                        ).license(new License().name("MIT"))
                        .title("Eventa")
                        .version("2"));
    }
}
