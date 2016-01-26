/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.cag.labs.raceadmin.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private Logger log = LoggerFactory.getLogger(SwaggerConfig.class.getName());

  @Bean
  public Docket labApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("se.cag.labs"))
      .paths(regex("/.*"))
      .build()
      .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfo(
      "C.A.G Contactor Race Administrator API",
      "This is the API for administrating races in the C.A.G Contactor jFocus Sphero race.",
      jarVersion(),
      "Use at your own risk!",
      "contactor@cag.se",
      "C.A.G Monster License",
      "http://www.apache.org/licenses/LICENSE-2.0");
    return apiInfo;
  }

  private String jarVersion() {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
    Properties prop = new Properties();
    try {
      prop.load(is);
    } catch (IOException ex) {
      log.error(null, ex);
    }
    return prop.getProperty("Implementation-Version", "0.0.1-SNAPSHOT");
  }

}
