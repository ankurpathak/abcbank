package com.github.ankurpathak.transactionservice.controller

import com.github.ankurpathak.transactionservice.dao.CreditDebitDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(name = "ACCOUNT-SERVICE")
interface AccountRestClient {
    @PostMapping("/v2/operations/credit")
    fun credit(@RequestBody data: CreditDebitDto, @RequestHeader("Authorization")token: String) : CreditDebitDto

    @PostMapping("/v2/operations/debit")
    fun debit(@RequestBody data: CreditDebitDto, @RequestHeader("Authorization") token: String) : CreditDebitDto
}