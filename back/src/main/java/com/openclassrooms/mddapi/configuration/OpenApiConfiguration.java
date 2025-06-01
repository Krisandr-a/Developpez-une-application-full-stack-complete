package com.openclassrooms.mddapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Krisandra ADAMS");
        myContact.setEmail("fake.email@gmail.com");

        Info information = new Info()
                .title("API ChâTop")
                .version("1.0")
                .description("Cette API expose des endpoints pour la gestion des locations, des utilisateurs et des messages.<br><br>"
                        + "Pour tester l'API, il faut s'inscrire et se connecter via les endpoints dédiés dans la section « S'inscrire et se connecter ». "
                        + "Une fois connecté, vous recevrez un jeton JWT dans la réponse. "
                        + "Copiez le jeton, cliquez sur le bouton « Authorize » en haut de la page "
                        + "et collez le jeton pour avoir accès aux autres endpoints.")
                .contact(myContact);

        // For JWT Authentication
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication")
                .in(SecurityScheme.In.HEADER);

        return new OpenAPI()
                .info(information)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .schemaRequirement("bearerAuth", securityScheme)
                .servers(List.of(server));
    }
}
