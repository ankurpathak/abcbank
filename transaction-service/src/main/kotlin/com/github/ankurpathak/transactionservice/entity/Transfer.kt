package com.github.ankurpathak.transactionservice.entity

import com.github.ankurpathak.transactionservice.enums.TransferStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.math.BigInteger

@Entity
@Table(name = "transfers")
data class Transfer(
        @Column(name = "amount", nullable = false)
        val amount: BigDecimal = BigDecimal.ZERO,

        @Column(name = "to_account_no", nullable = false)
        val toAccountNo: String,

        @Column(name = "from_account_no", nullable = false)
        val fromAccountNo: String,

        @Column(name = "debit_status", nullable = false)
        @Enumerated(EnumType.STRING)
        var debitStatus: TransferStatus = TransferStatus.NONE,

        @Column(name = "credit_status", nullable = false)
        @Enumerated(EnumType.STRING)
        var creditStatus: TransferStatus = TransferStatus.NONE,

        @Column(name = "status", nullable = false)
        @Enumerated(EnumType.STRING)
        var status: TransferStatus = TransferStatus.NONE,

        @Id @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "transfer_id", nullable = false)
        var id: String? = null
)