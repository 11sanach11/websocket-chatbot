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
    public boolean onConnectFailure(Exception exception) {
        counter++;
        log.warn("### Reconnecting after connect failure... (reconnect count: {}) {}", counter, exception.getMessage());
        return true;
    }
}
