package com.example.posts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    public WebClient getClient(){
        return WebClient.create();
    }
}
