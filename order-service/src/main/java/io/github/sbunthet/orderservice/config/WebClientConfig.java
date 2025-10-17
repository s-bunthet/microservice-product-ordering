package io.github.sbunthet.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    /**
     * For load balancing, we need to return a WebClient.Builder bean, because WebClient itself is immutable.
     * This allows Spring Cloud to intercept the builder and configure it with a load-balancing filter.
     * **/
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
