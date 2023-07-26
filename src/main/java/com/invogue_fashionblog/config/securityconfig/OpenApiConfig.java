package com.invogue_fashionblog.config.securityconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenApiConfig {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer "+openaiApiKey);
            return execution.execute(request, body);
        });
        return template;
    }

}
