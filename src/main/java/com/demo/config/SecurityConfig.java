package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

     private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c-> c.disable()).cors(c-> c.disable());
        http.addFilterBefore(jwtFilter , AuthorizationFilter.class);
        http.authorizeHttpRequests(auth-> auth.requestMatchers("/security/**").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}
