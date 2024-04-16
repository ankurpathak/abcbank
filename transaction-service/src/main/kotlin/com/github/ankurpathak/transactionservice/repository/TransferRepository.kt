package com.github.ankurpathak.transactionservice.repository

import com.github.ankurpathak.transactionservice.entity.Transfer
import io.micrometer.observation.annotation.Observed
import org.springframework.data.jpa.repository.JpaRepository

@Observed
interface TransferRepository : JpaRepository<Transfer, String>{
}