package com.caa.chatbot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * Created by nihil on 08.09.17.
 */
@Component
public class WebSocketHandler {

    private static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @Value("${chatbot.frequency:1000}")
    private long frequency;

    @Value("${chatbot.uri:wss://localhost}")
    private URI uri;

    @PostConstruct
    public void init() {
        log.info("Parameters: frequency: {} ms, URI: {}", frequency, uri);
    }
}
