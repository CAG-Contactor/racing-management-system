package se.cag.labs.cagrms.clientapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@SpringBootApplication
@EnableWebSocket
public class ClientApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientApiApplication.class, args);
  }
}