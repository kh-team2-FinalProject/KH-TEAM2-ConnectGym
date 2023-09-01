package com.khteam2.connectgym.common;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    public static int MAX_CONNECTION_TOTAL = 200;
    public static int MAX_CONNECTION_PER_ROUTE = 50;
    public static int READ_TIMEOUT = 10000;
    public static int CONNECT_TIMEOUT = 5000;
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        HttpClient httpClient = HttpClientBuilder.create()
            .setMaxConnTotal(MAX_CONNECTION_TOTAL)
            .setMaxConnPerRoute(MAX_CONNECTION_PER_ROUTE)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return builder
            .requestFactory(() -> factory)
            .setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
            .setConnectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
            .build();
    }
}
