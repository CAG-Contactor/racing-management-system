package se.cag.labs.cagrms.clientapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import se.cag.labs.cagrms.clientapi.eventbus.EventChannelSocketHandler;

@Configuration
public class WebsocketConfig implements WebSocketConfigurer {
  @Autowired
  private EventChannelSocketHandler eventBus;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(eventBus, EventChannelSocketHandler.CHANNEL_NAME)
      .setAllowedOrigins("http://localhost:3000", "http://192.168.0.50:30080")
      .withSockJS()
    ;
  }

}
