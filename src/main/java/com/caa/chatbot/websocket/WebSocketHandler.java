package com.caa.chatbot.websocket;

import com.caa.chatbot.websocket.ChatbotHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

/**
 * Created by nihil on 09.09.17.
 */
@ClientEndpoint
public class WebSocketHandler {

    private final static Logger log = LoggerFactory.getLogger(ChatbotHandler.class);

    @OnMessage
    public void onMessage(String message) {
        log.info("Received msg: {}", message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("Close websocket for session with id: {}, closeReason: {}", session.getId(), closeReason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }
}
