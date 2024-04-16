package com.github.ankurpathak.accountservice.dto;

import com.github.ankurpathak.accountservice.enums.Currency;
import com.github.ankurpathak.accountservice.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationDTO(String id, LocalDateTime date, OperationType type, BigDecimal amount,
                           Currency currency, String description, String accountId) {
}
