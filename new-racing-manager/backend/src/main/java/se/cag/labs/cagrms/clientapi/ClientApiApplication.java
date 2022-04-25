package se.cag.labs.cagrms.clientapi;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableWebSocket
public class ClientApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientApiApplication.class, args);
  }
}
