package com.github.ankurpathak.accountservice.controller;


import com.github.ankurpathak.accountservice.dto.CustomerDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for communicating with the "CUSTOMER-SERVICE" service.
 *
 * <p>This interface defines methods for retrieving customer information via HTTP requests.</p>
 *
 * <p>Author: Ankur Pathak</p>
 *
 * @FeignClient(name = "CUSTOMER-SERVICE")
 */
@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    /**
     * Retrieves customer details by their unique ID.
     *
     * @param id The unique identifier of the customer.
     * @return The {@code CustomerDTO} containing customer details.
     */
    @GetMapping("/v2/customers/get/{id}")
    CustomerDTO getById(@PathVariable String id);
}
