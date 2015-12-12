package se.cag.labs.cagrms.clientapi.config;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import static springfox.documentation.builders.PathSelectors.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                BuildInfo.getTitle(),
                BuildInfo.getDescription(),
                BuildInfo.getVersion(),
                "",
                "",
                "",
                "");
    }
}