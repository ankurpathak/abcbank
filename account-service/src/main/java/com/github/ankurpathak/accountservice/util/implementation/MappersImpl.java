package com.github.ankurpathak.accountservice.util.implementation;

import com.github.ankurpathak.accountservice.dto.AccountDTO;
import com.github.ankurpathak.accountservice.dto.OperationDTO;
import com.github.ankurpathak.accountservice.entity.Account;
import com.github.ankurpathak.accountservice.entity.Operation;
import com.github.ankurpathak.accountservice.util.Mappers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class MappersImpl implements Mappers {
    /**
     * Converts an {@code AccountDTO} to an {@code Account}.
     *
     * @param dto The {@code AccountDTO} to be converted.
     * @return The corresponding {@code Account} object.
     */
    @Override
    public Account fromAccountDTO(@NotNull AccountDTO dto) {
        return Account.builder().id(dto.id()).customerId(dto.customerId())
                .balance(dto.balance()).currency(dto.currency())
                .status(dto.status()).lastUpdate(dto.lastUpdate())
                .creation(dto.creation()).build();
    }

    /**
     * Converts an {@code Account} to an {@code AccountDTO}.
     *
     * @param account The {@code Account} to be converted.
     * @return The corresponding {@code AccountDTO} object.
     */
    @Override
    public AccountDTO fromAccount(@NotNull Account account) {
        return new AccountDTO(account.getId(), account.getCustomerId(),
                account.getBalance(), account.getCurrency(),
                account.getStatus(), account.getCreation(),
                account.getLastUpdate()
        );
    }

    /**
     * Converts an {@code OperationDTO} to an {@code Operation}.
     *
     * @param dto The {@code OperationDTO} to be converted.
     * @return The corresponding {@code Operation} object.
     */
    @Override
    public Operation fromOperationDTO(@NotNull OperationDTO dto) {
        Account account = new Account();
        account.setId(dto.accountId());
        return Operation.builder().id(dto.id()).currency(dto.currency())
                .amount(dto.amount()).description(dto.description())
                .date(dto.date()).type(dto.type()).account(account)
                .build();
    }

    /**
     * Converts an {@code Operation} to an {@code OperationDTO}.
     *
     * @param operation The {@code Operation} to be converted.
     * @return The corresponding {@code OperationDTO} object.
     */
    @Override
    public OperationDTO fromOperation(@NotNull Operation operation) {
        return new OperationDTO(operation.getId(), operation.getDate(),
                operation.getType(), operation.getAmount(),
                operation.getCurrency(), operation.getDescription(),
                operation.getAccount().getId()
        );
    }
}