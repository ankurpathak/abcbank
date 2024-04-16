package com.github.ankurpathak.accountservice.controller;


import com.github.ankurpathak.accountservice.dto.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * Feign client interface for communicating with the "NOTIFICATION-SERVICE" service.
 *
 * <p>This interface defines methods for sending email via HTTP requests.</p>
 *
 * <p>Author: Ankur Pathak</p>
 *
 * @FeignClient(name = "NOTIFICATION-SERVICE")
 */
@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationRestClient {

    /**
     * Retrieves customer details by their unique ID.
     *
     * @param emailDto represents essential info to send email
     */
    @PostMapping("/emails")
    Map<String, String> sendEmail(@RequestBody EmailDto emailDto, @RequestHeader("Authorization") String token);
}
