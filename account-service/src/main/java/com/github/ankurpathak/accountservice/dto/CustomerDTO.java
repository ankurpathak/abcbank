package com.github.ankurpathak.accountservice.dto;

import com.github.ankurpathak.accountservice.enums.Sex;

import java.time.LocalDateTime;
import java.util.Date;

public record CustomerDTO(String id,
                          String firstname,
                          String name,
                          String placeOfBirth,
                          Date dateOfBirth,
                          String nationality,
                          Sex sex,
                          String cin,
                          String email,
                          String phone,
                          LocalDateTime creation,
                          LocalDateTime lastUpdate) {
}
