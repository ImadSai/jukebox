package com.theoctavegroup.jukeboxsettings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Configuration
 */
@Configuration
public class SwaggerConfiguration {

    public static final String JUKEBOX_SETTING_CONTROLLER = "Jukeboxes Setting Controller";

    /**
     * Define API Information
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Jukeboxes Settings").version("1.0.0")
                .description("This page documents Jukeboxes RESTful Web Service Endpoints")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.theoctavegroup.jukeboxsettings.controller"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(JUKEBOX_SETTING_CONTROLLER, ""));
    }

}
