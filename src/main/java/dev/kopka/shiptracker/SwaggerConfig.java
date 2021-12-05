package dev.kopka.shiptracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket SwaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ship-tracker-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ship Tracker API")
                .description("This API allow you, to get information about ships near Norway. " +
                        "It based on free APIs: AIS (www.barentswatch.no) and location (positionstack.com)")
                .contact(new Contact("Jakub Kopka",
                        "http://www.kopka.dev",
                        "Jakub@Kopka.dev"))
                .version("0.0.1-SNAPSHOT")
                .build();
    }

}
