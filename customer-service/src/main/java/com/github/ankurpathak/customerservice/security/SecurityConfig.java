package com.github.ankurpathak.customerservice.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final JWTAuthorizationFilter authorizationFilter;

    public SecurityConfig(JWTAuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v2/customers/save/**").hasAuthority(ADMIN)
                        .requestMatchers("/v2/customers/update/**").hasAuthority(ADMIN)
                        .requestMatchers("/v2/customers/all/**").hasAuthority(ADMIN)
                        .requestMatchers("/v2/customers/{page}/{size}/search/**").hasAuthority(ADMIN)
                        .requestMatchers("/v2/customers/delete/**").hasAuthority(ADMIN)
                        .requestMatchers("/v2/customers/get/**").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                ).addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
