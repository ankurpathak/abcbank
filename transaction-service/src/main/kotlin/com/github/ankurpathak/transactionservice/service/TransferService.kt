package com.github.ankurpathak.transactionservice.service

import com.github.ankurpathak.transactionservice.controller.AccountRestClient
import com.github.ankurpathak.transactionservice.dao.CreditDebitDto
import com.github.ankurpathak.transactionservice.dao.TransferDao
import com.github.ankurpathak.transactionservice.entity.Transfer
import com.github.ankurpathak.transactionservice.enums.TransferStatus
import com.github.ankurpathak.transactionservice.repository.TransferRepository
import org.springframework.stereotype.Service

@Service
class TransferService(val dao: TransferRepository, val accountRestClient: AccountRestClient) {
    fun transfer(data: TransferDao, token: String) {
        val transfer = Transfer(data.amount, data.fromAccount, data.toAccount)
        dao.save(transfer)
        transfer.apply {
            debitStatus = TransferStatus.INITIATED
            status = TransferStatus.INITIATED
        }
        dao.save(transfer)
        try {
            val debit = CreditDebitDto(data.fromAccount, "Debit for transfer to beneficiary ${data.toAccount}", data.amount)
            accountRestClient.debit(debit, token)
            transfer.apply { debitStatus = TransferStatus.SUCCESS }
            dao.save(transfer)
        } catch (ex: Exception) {
            transfer.apply { debitStatus = TransferStatus.FAILURE
                status = TransferStatus.FAILURE
            }
            dao.save(transfer)
            throw ex
        }

        transfer.apply { creditStatus = TransferStatus.INITIATED }
        dao.save(transfer)
        try{
            val credit = CreditDebitDto(data.toAccount, "Credit for transfer from beneficiary ${data.fromAccount}", data.amount)
            accountRestClient.credit(credit, token);
            transfer.apply {
                creditStatus = TransferStatus.SUCCESS
                status = TransferStatus.SUCCESS
            }
            dao.save(transfer)
        }catch (ex: Exception){
            transfer.apply { creditStatus = TransferStatus.FAILURE
                status = TransferStatus.FAILURE
            }
            dao.save(transfer)
            throw ex
        }

    }
}