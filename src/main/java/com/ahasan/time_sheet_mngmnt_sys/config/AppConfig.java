package com.ahasan.time_sheet_mngmnt_sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // EMPLOYEE APIs
                        .requestMatchers(
                                "/api/employees/register",
                                "/api/employees/login",
                                "/api/employees/timesheet/**"
                        ).permitAll()

                        // MANAGER APIs
                        .requestMatchers(
                                "/manager/**"
                        ).permitAll()

                        // ALL OTHER APIs
                        .anyRequest()
                        .permitAll()
                )

                // DISABLE BASIC AUTH
                .httpBasic(httpBasic -> httpBasic.disable())

                // DISABLE FORM LOGIN
                .formLogin(form -> form.disable());

        return http.build();
    }
}