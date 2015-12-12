/*
 * User: joel
 * Date: 2015-12-11
 * Time: 23:30
 */
package se.cag.labs.cagrms.clientapi;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.*;
import org.springframework.boot.context.web.*;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class ClientApiApplication
        extends SpringBootServletInitializer
        implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(eventEmitter(), EventBus.CHANNEL_NAME)
                .setAllowedOrigins("*")
                .withSockJS()
        ;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ClientApiApplication.class);
    }

    @Bean
    public WebSocketHandler eventEmitter() {
        return new EventBus();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApiApplication.class, args);
    }
}