package io.github.sbunthet.discoveryserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Use EnableWebSecurity for Servlet-based applications. Why? Because Discovery Server uses Spring MVC, not WebFlux.
public class SecurityConfig {
    @Value("${eureka.username}")
    private String eurekaUser;
    @Value("${eureka.password}")
    private String eurekaPass;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(eurekaUser)
                .password(eurekaPass)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user); // In-memory user store during runtime
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // For simplicity, no encoding (plain text). Replace with BCrypt in production!
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
