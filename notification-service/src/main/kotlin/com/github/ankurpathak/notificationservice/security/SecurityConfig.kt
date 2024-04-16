package com.github.ankurpathak.notificationservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val authorizationFilter: JWTAuthorizationFilter) {


    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
                .authorizeHttpRequests {
                    it.requestMatchers("/emails/**").hasAuthority(ADMIN)
                            .requestMatchers(*AUTH_WHITELIST).permitAll()
                }
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter::class.java).build()

    }


    companion object {
        @JvmField
        val ADMIN = "ADMIN"

        @JvmField
        val AUTH_WHITELIST = arrayOf(
                "/v3/api-docs/**",
                "/swagger-ui/**"
        )
    }

}
