package se.cag.labs.cagrms.clientapi.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newConcurrentMap;

@Slf4j
@Component
public class EventChannelSocketHandler extends TextWebSocketHandler {

  public static final String CHANNEL_NAME = "/eventchannel";
  private Map<String, WebSocketSession> activeSessions = newConcurrentMap();

  public EventChannelSocketHandler() {
    log.debug("Created:" + this);
  }

  public void broadcastMessage(String msg) {
    log.debug("Broadcasting messages:" + msg);
    activeSessions.values()
      .stream()
      .forEach(session -> {
        try {
          log.debug("Send message: " + msg + " to: " + session.getId());
          session.sendMessage(new TextMessage(msg));
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
  }

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
    log.debug("Session created: " + session.getId());
    activeSessions.put(session.getId(), session);
  }

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
    log.debug("Session ended: " + session.getId());
    activeSessions.remove(session.getId());
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
    throws Exception {
    System.out.println("Message received:" + message.getPayload());
  }
}