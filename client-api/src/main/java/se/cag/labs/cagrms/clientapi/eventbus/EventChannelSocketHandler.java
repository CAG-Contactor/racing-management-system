package se.cag.labs.cagrms.clientapi.eventbus;

import lombok.extern.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

import java.io.*;
import java.util.*;

import static com.google.common.collect.Maps.*;

@Log4j
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
                        log.debug("Send message: "+msg+" to: "+session.getId());
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