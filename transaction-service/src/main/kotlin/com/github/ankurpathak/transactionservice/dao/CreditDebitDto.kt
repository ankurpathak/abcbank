package com.github.ankurpathak.transactionservice.dao

import java.math.BigDecimal
import java.math.BigInteger

data class CreditDebitDto(val accountId: String, val description: String, val amount: BigDecimal, var customerId: String? =  null)