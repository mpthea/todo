package com.example.todolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Value("${api.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI defineOpenAPI() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("Development");

        Contact contact = new Contact();
        contact.setName("Имя Фамилия");
        contact.setEmail("my.email@example.com");

        Info info = new Info()
                .title("Система управления задачами")
                .version("1.0")
                .description("API для управления задачами")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
