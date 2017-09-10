package com.caa.chatbot.websocket;

import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;

/**
 * Created by nihil on 10.09.17.
 */
@Component
public class ChatbotReconnectHandler extends ClientManager.ReconnectHandler {
    private final static Logger log = LoggerFactory.getLogger(ChatbotReconnectHandler.class);
    private int counter = 0;

    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        counter++;
        if (counter <= 3) {
            log.warn("### Reconnecting... (reconnect count: {})", counter);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onConnectFailure(Exception exception) {
        counter++;
        if (counter <= 3) {
            log.warn("### Reconnecting... (reconnect count: {}) {}", counter, exception.getMessage());

            // Thread.sleep(...) or something other "sleep-like" expression can be put here - you might want
            // to do it here to avoid potential DDoS when you don't limit number of reconnects.
            return true;
        } else {
            return false;
        }
    }
}
