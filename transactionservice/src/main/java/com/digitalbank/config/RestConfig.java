package com.digitalbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class to create and expose a RestTemplate bean.
 * This allows the RestTemplate to be dependency injected into services
 * like TransactionService, following Spring best practices.
 */
@Configuration
public class RestConfig {

    /**
     * Creates and returns a new RestTemplate bean.
     * @return a RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
