package com.surya.dansbetest.configuration;

import com.surya.dansbetest.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilConfig {
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
