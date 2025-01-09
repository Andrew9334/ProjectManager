package com.piron.projectmanagertest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключение CSRF для REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/projects/**").hasRole("USER") // Ограничение доступа к путям
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Новый способ настройки базовой аутентификации
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password") // {noop} означает использование пароля в виде открытого текста (не рекомендуется в продакшене)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

