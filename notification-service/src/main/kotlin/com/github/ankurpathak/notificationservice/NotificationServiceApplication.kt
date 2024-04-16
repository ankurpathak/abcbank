package com.github.ankurpathak.notificationservice

import com.github.ankurpathak.notificationservice.service.EmailService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@SpringBootApplication
class NotificationServiceApplication

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}


