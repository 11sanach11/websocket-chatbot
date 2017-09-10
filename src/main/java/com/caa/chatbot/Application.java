package com.caa.chatbot;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nihil on 09.09.17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class Application {

    private static Object waitLock = new Object();

    @Bean
    public Gson gson(){
        return new Gson();
    }

    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        wait4TerminateSignal();
    }
}