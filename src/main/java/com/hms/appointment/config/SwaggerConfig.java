package com.hms.appointment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI configuration.
 * Access the UI at: http://localhost:8083/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI appointmentServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HMS - Appointment Service API")
                        .description("Hospital Management System - Appointment Service\n\n" +
                                "Manages all patient appointments with doctors across departments.\n\n" +
                                "**Status values:** SCHEDULED | COMPLETED | CANCELLED | NO_SHOW")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("HMS Team - Member 4")
                                .email("member4@hms.com")));
    }
}
