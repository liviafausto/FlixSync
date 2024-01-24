package com.flixsync.FlixSync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "FlixSync", description = "FlixSync documentation", version = "2.3.0"))
public class FlixSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlixSyncApplication.class, args);
	}

}
