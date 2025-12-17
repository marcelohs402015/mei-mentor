package com.meimentor.customer.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration.
 * 
 * <p>This configuration sets up the API documentation using SpringDoc OpenAPI.</p>
 * 
 * @author MEI-Mentor Team
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI meiMentorOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MEI-Mentor API")
                        .description("API RESTful para análise de oportunidades de formalização MEI. " +
                                   "Identifica empreendedores informais através de análise de transações " +
                                   "e calcula economia potencial com formalização.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MEI-Mentor Team")
                                .email("contato@meimentor.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

