package com.github.ankurpathak.notificationservice.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(val mailSender: JavaMailSender) {
    /**
     * This method will send compose and send a new message
     */
    fun send(to: String?, subject: String?, body: String?) {
        val message = SimpleMailMessage()
        message.from = "ankurpathak@live.in"
        message.setTo(to)
        message.subject = subject
        message.text = body
        mailSender.send(message)
    }
}