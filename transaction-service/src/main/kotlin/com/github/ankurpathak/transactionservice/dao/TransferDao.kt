package com.github.ankurpathak.transactionservice.dao

import java.math.BigDecimal
import java.math.BigInteger

data class TransferDao(val amount: BigDecimal, val toAccount: String, val fromAccount: String)