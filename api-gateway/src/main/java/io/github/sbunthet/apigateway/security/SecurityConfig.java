package io.github.sbunthet.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Use EnableWebFluxSecurity for WebFlux applications
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
      http.csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for simplicity
              .authorizeExchange(
                      exchange ->  exchange.
                              pathMatchers("/eureka/**").permitAll(). // Allow access to Eureka dashboard
                              anyExchange().authenticated()) // Require authentication for all other requests
              .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // Enable JWT-based authentication
      return http.build();
    }
}
