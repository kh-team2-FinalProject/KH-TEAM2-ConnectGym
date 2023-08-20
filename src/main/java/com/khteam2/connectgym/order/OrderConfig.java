package com.khteam2.connectgym.order;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
    @Value("${portone.api_key}")
    private String apiKey;
    @Value("${portone.api_secret}")
    private String apiSecret;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(this.apiKey, this.apiSecret);
    }
}
