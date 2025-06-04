package com.erico.desafio.itau.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Érico O. Ribeiro",
                        email = "oliv.ericorib@gmail.com",
                        url = "https://www.github.com/ericolivrib"
                ),
                description = "API de cadastro e estatísticas de transações",
                title = "Desafio Itaú Unibanco - Érico",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Servidor local",
                        url = "/"
                )
        }
)
public class OpenAPIConfig {
}
