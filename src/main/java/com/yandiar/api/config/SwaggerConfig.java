package com.yandiar.api.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.not;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Jimmy Rengga
 */
@Configuration
@EnableSwagger2
//@Profile("dev") // if you want using swagger just on development mode
public class SwaggerConfig {

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest-api-springboot-postgresql")
                .apiInfo(apiInfo())
                .select()
                .paths(exceptErrorPaths())
                .build();
    }

    private Predicate<String> exceptErrorPaths() {
        return not(PathSelectors.regex("/error"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("LEARNING REST API")
                .description("LEARNING REST API")
                .termsOfServiceUrl("yandiar.co.id")
                .licenseUrl("yandiar.co.id")
                .contact(new Contact("Dev", "", "yandiar.rohman@gmail.com"))
                .license("Open Source")
                .version("1.0.0")
                .build();
    }
}