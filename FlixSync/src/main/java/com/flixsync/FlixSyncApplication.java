package com.flixsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "FlixSync", description = "OpenApi documentation for FlixSync", version = "v1.0.0"),
		servers = @Server(url = "http://localhost:8080", description = "LOCAL ENVIRONMENT")
)
public class FlixSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlixSyncApplication.class, args);
	}

}
