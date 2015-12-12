package se.cag.labs.cagrms.clientapi;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

import java.io.*;
import java.util.*;

public class EventBus extends TextWebSocketHandler {

    public static final String CHANNEL_NAME = "/eventbus";
    private Timer timer;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        timer = new Timer("Event bus");
        timer.schedule(
                new TimerTask() {
                    int n = 10;

                    @Override
                    public void run() {
                        if (n-- <= 0) {
                            System.out.println("10 times, cancelling...");
                            timer.cancel();
                        }
                        TextMessage message = new TextMessage("{\"type\":\"TEST\",\"time\":\""+new Date()+"\"}");
                        try {
                            session.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Date(System.currentTimeMillis() + 10000),
                10000
        );
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        System.out.println("canelling:" + message.getPayload());
        timer.cancel();
        session.close();
    }

}