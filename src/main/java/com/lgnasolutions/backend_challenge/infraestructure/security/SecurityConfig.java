package com.lgnasolutions.backend_challenge.infraestructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Basic security config: permit all for /auth endpoints, secure others
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("HttpMethod.POST", "/api/auth/login").permitAll()
                        .requestMatchers("HttpMethod.POST", "/api/auth/register").permitAll()
                        .requestMatchers("/api/notes/**").permitAll()
                        .requestMatchers("/api/tags/**").permitAll()
                        .requestMatchers("/api/filter-state/**").permitAll()
                        .requestMatchers("/api/notes/**").permitAll()
                        .requestMatchers("/api/notes/{id}/edit").permitAll()
                        .requestMatchers("/api/note-versions/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/index.html","/swagger-ui/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
