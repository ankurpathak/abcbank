package com.github.ankurpathak.transactionservice.controller

import com.github.ankurpathak.transactionservice.dao.TransferDao
import com.github.ankurpathak.transactionservice.service.TransferService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transfers")
class TransferRestController(val service: TransferService) {

    @PostMapping
    fun transfer(@RequestBody data: TransferDao, @RequestHeader("Authorization") token: String){
        service.transfer(data, token)
    }
}