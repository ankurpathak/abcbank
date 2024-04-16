package com.github.ankurpathak.notificationservice

import com.github.ankurpathak.notificationservice.dto.EmailDto
import com.github.ankurpathak.notificationservice.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/emails")
class EmailController(val service: EmailService) {
    @PostMapping
    fun send(@RequestBody data: EmailDto): ResponseEntity<Map<String, String>>{
        service.send(data.to, data.subject, data.body)
        return ResponseEntity.ok(mapOf("result" to "SUCCESS"))
    }
}