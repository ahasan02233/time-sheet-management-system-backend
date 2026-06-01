package com.ahasan.time_sheet_mngmnt_sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

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

                .cors(cors -> {
                })

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC APIs
                        // =========================
                        .requestMatchers(
                                "/api/employees/register",
                                "/api/employees/login",
                                "/api/employees/timesheet/create"
                        ).permitAll()

                        // =========================
                        // SWAGGER APIs
                        // =========================
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/webjars/**"
                        ).permitAll()

                        // =========================
                        // MANAGER TIMESHEET APIs
                        // =========================
                        .requestMatchers(
                                "/manager/pending",
                                "/manager/approve/**",
                                "/manager/reject/**"
                        )
                        .hasAnyRole("MANAGER", "ADMIN")

                        // =========================
                        // MANAGER LEAVE APIs
                        // =========================
                        .requestMatchers(
                                "/manager/leave/pending",
                                "/manager/leave/*/approve",
                                "/manager/leave/*/reject"
                        )
                        .hasAnyRole("MANAGER", "ADMIN")

                        // =========================
                        // EMPLOYEE TIMESHEET APIs
                        // =========================
                        .requestMatchers(
                                "/api/employees/timesheet/my"
                        )
                        .authenticated()

                        // =========================
                        // EMPLOYEE LEAVE APIs
                        // =========================
                        .requestMatchers(
                                "/api/employees/leave/apply",
                                "/api/employees/leave/my"
                        )
                        .authenticated()

                        // =========================
                        // ALL OTHER APIs
                        // =========================
                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(httpBasic -> httpBasic.disable())

                .formLogin(form -> form.disable())

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}