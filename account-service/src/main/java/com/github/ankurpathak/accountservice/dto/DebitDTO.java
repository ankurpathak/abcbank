package com.github.ankurpathak.accountservice.dto;

import java.math.BigDecimal;

public record DebitDTO(String accountId, String description, BigDecimal amount, String customerId) {
}
