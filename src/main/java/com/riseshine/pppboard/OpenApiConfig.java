package com.riseshine.pppboard;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/")})

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();
        return new OpenAPI().components(components);
    }
}
