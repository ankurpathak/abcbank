package com.github.ankurpathak.accountservice.controller;

import com.github.ankurpathak.accountservice.dto.*;
import com.github.ankurpathak.accountservice.exception.AccountNotActivatedException;
import com.github.ankurpathak.accountservice.exception.AccountNotFoundException;
import com.github.ankurpathak.accountservice.exception.BalanceNotSufficientException;
import com.github.ankurpathak.accountservice.exception.OperationNotFoundException;
import com.github.ankurpathak.accountservice.service.OperationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/operations")
public class OperationRestController {

    private final OperationService operationService;
    private final CustomerRestClient customerRestClient;

    private final NotificationRestClient notificationRestClient;

    public OperationRestController(OperationService operationService, CustomerRestClient customerRestClient, NotificationRestClient notificationRestClient) {
        this.operationService = operationService;
        this.customerRestClient = customerRestClient;
        this.notificationRestClient = notificationRestClient;
    }


    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO, @RequestHeader("Authorization") String token) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException {
        final var credit = operationService.credit(creditDTO);
        final var customer = customerRestClient.getById(credit.customerId());
        final var email = new EmailDto(customer.email(), "Credit", String.format("Your account %s is credited with INR %s.", credit.accountId(), credit.amount()));
        notificationRestClient.sendEmail(email, token);
        return credit;
    }

    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO, @RequestHeader("Authorization") String token) throws AccountNotFoundException, BalanceNotSufficientException, AccountNotActivatedException{
        final var debit = operationService.debit(debitDTO);
        final var customer = customerRestClient.getById(debit.customerId());
        final var email = new EmailDto(customer.email(), "Debit", String.format("Your account %s is debited with INR %s.", debit.accountId(), debit.amount()));
        notificationRestClient.sendEmail(email, token);
        return debit;
    }

    @GetMapping("/get/{id}")
    public OperationDTO getById(@PathVariable String id) throws OperationNotFoundException {
        return operationService.getById(id);
    }

    @GetMapping("/{accountId}/all")
    public HistoryDTO getHistory(@PathVariable String accountId,
                                 @RequestParam(name ="page", defaultValue = "0") int page,
                                 @RequestParam(name ="size", defaultValue = "5") int size) throws AccountNotFoundException{
        return operationService.getHistory(accountId, page, size);
    }
}
