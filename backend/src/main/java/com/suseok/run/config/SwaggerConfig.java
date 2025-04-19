package com.suseok.run.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI multiAuthOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("SUSEOK PROJECT")
						.description("수석프로젝트 REST API입니다\nAPI Documentation for your application")
						.version("v0.0.1")
						.license(new License().name("SSAFY").url("https://www.ssafy.com")))
				.addSecurityItem(new SecurityRequirement().addList("JWT"))
				.components(new Components()
						.addSecuritySchemes("JWT", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.name("JWT")
						)
						.addSecuritySchemes("basicAuth", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("basic")
						)
				);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("public")
				.pathsToMatch("/**")
				.build();
	}
}
