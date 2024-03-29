package se.cag.labs.cagrms.clientapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("se.cag.labs"))
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
