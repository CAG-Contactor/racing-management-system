package se.cag.labs.cagrms.clientapi.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.socket.config.annotation.*;
import se.cag.labs.cagrms.clientapi.eventbus.*;

@Configuration
public class WebsocketConfig implements WebSocketConfigurer {
  @Autowired
  private EventChannelSocketHandler eventBus;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(eventBus, EventChannelSocketHandler.CHANNEL_NAME)
      .setAllowedOrigins("*")
      .withSockJS()
    ;
  }

}
