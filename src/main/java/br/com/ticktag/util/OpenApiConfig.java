package br.com.ticktag.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API TickTag")
						.version("v1")
						.description("Serviço relacionados a gestão de tickets")
						.termsOfService("")
						.license(
								new License()
									.name("Apache 2.0")
									.url("")));
	}

}