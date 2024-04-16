package com.github.ankurpathak.notificationservice.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SecParams(
    @Value("\${secret}")
    val secret: String? = null,

    @Value("\${expired-time}")
    val expiredTime: Long? = null
)
