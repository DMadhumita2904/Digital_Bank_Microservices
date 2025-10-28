package com.digitalbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DigitalbankAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalbankAccountServiceApplication.class, args);
    }

    // ✅ RestTemplate bean for inter-service communication
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
