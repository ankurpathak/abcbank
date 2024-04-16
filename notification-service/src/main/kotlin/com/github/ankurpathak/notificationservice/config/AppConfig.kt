package com.github.ankurpathak.notificationservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class AppConfig {
    @Bean
    fun logger(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeHeaders(true)
        filter.setIncludeClientInfo(true)
        filter.setIncludePayload(true)
        filter.setIncludeQueryString(true)
        return filter
    }
}