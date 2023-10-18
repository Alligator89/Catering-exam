package com.exam.catering;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Catering",
                description = "This is Catering project",
                contact = @Contact(
                        name = "Liubenkov Denis",
                        email = "denisliubenkov@yandex.by",
                        url = "@liubenkovdenis"
                )
        )
)
@SpringBootApplication
public class CateringExamProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CateringExamProjectApplication.class, args);
    }

}
