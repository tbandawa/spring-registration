package org.gutaramwari.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class RegistrationConfig {
	
	@Bean
	public OpenAPI bookingOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Attendants Registration APIs")
	            .description("<b>Endpoints for managing event attendants</b>")
	            .version("v0.0.1")
	            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	            .externalDocs(new ExternalDocumentation()
	            .description("GitHub Page")
	            .url("https://github.com/tbandawa/spring-registration"));
	}

}
