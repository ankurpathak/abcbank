package com.github.ankurpathak.accountservice.dto;

import com.github.ankurpathak.accountservice.enums.AccountStatus;
import com.github.ankurpathak.accountservice.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(String id, String customerId, BigDecimal balance, Currency currency,
                         AccountStatus status, LocalDateTime creation, LocalDateTime lastUpdate) {
}
