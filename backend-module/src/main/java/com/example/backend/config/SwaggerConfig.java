package com.example.backend.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
	    info = @Info(
	        title = "API de Benefícios",
	        version = "1.0",
	        description = "API REST para gerenciamento de benefícios e transferências"
	    )
	)
public class SwaggerConfig {
   
}