package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info=@Info(
                title = "Blog Application",

                description = "contains all the post,category,comments,user",
                contact= @Contact(
                        name = "Bhanjuri pranathi",
                email = "pranathibanuri9@gmail.com"
        ),
                termsOfService="B&P",
                version="v1"

        ),
        servers={
                @Server(
                        description = "Dev",
                        url = "http://localhost:8080"
                )
},
security= @SecurityRequirement(name = "bearerAuth")


)
@SecurityScheme(
        name = "bearerAuth",
        in= SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",

        scheme="bearer"

)
public class SwaggerConfig{

}