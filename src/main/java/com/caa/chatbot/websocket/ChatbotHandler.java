package com.caa.chatbot.websocket;

import com.caa.chatbot.domain.Message;
import com.google.gson.Gson;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by nihil on 09.09.17.
 */
@Component
public class ChatbotHandler {

    private final static Logger log = LoggerFactory.getLogger(ChatbotHandler.class);

    @Value("${chatbot.frequency}")
    private long frequency;

    @Value("${chatbot.uri}")
    private URI uri;

    @Value("${chatbot.message.name}")
    private String messageName;

    @Value("${chatbot.message.text}")
    private List<String> messageText;

    @Autowired
    private ChatbotReconnectHandler chatbotReconnectHandler;

    @Autowired
    private Gson gson;

    @PostConstruct
    public void init() {
        log.info("Init handler service. Parameters: frequency: {}, uri: {}", frequency, uri);
        Thread thread = new Thread(() -> {
            ClientManager clientManager = ClientManager.createClient();
            clientManager.getProperties().put(ClientProperties.RECONNECT_HANDLER, chatbotReconnectHandler);
            try (Session session = clientManager.connectToServer(WebSocketHandler.class, uri)) {
                int firstIndex = 0;
                while (true) {
                    Message message = new Message();
                    try {
                        message.setId(++firstIndex);
                        message.setName(messageName);
                        message.setMessage(String.join("\n", messageText));
                        log.info("Sending message: {}", message);
                        session.getAsyncRemote().sendText(gson.toJson(message));
                        Thread.sleep(frequency);
                    } catch (RuntimeException runtimeException) {
                        log.warn("Exception while sending message: {}: {}", message, runtimeException.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                log.info("Close client... Reason: {}", e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
