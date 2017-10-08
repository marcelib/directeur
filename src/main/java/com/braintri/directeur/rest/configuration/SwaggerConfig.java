package com.braintri.directeur.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket employeesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.braintri.directeur.rest.endpoints"))
                .paths(regex("/employees.*"))
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Directeur Application REST API",
                "API description for Directeur Application",
                "1.0",
                "Terms of service",
                new Contact("Marceli Baczewski", "https://pl.linkedin.com/in/marcelibaczewski", "marceli.baczewski@gmail.com"),
                "MIT License",
                "https://opensource.org/licenses/MIT");
        return apiInfo;
    }
}