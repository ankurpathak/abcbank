package com.github.ankurpathak.accountservice.service.implementation;


import com.github.ankurpathak.accountservice.dto.CreditDTO;
import com.github.ankurpathak.accountservice.dto.DebitDTO;
import com.github.ankurpathak.accountservice.dto.HistoryDTO;
import com.github.ankurpathak.accountservice.dto.OperationDTO;
import com.github.ankurpathak.accountservice.entity.Account;
import com.github.ankurpathak.accountservice.entity.Operation;
import com.github.ankurpathak.accountservice.enums.AccountStatus;
import com.github.ankurpathak.accountservice.enums.Currency;
import com.github.ankurpathak.accountservice.enums.OperationType;
import com.github.ankurpathak.accountservice.exception.OperationNotFoundException;
import com.github.ankurpathak.accountservice.repository.AccountRepository;
import com.github.ankurpathak.accountservice.repository.OperationRepository;
import com.github.ankurpathak.accountservice.util.Mappers;
import com.github.ankurpathak.accountservice.exception.AccountNotActivatedException;
import com.github.ankurpathak.accountservice.exception.AccountNotFoundException;
import com.github.ankurpathak.accountservice.exception.BalanceNotSufficientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OperationServiceImplTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private OperationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OperationServiceImpl(operationRepository, accountRepository, mappers);
    }

    @Test
    void credit() throws AccountNotFoundException, AccountNotActivatedException, BalanceNotSufficientException {
        LocalDateTime dateTime = LocalDateTime.now();
        String accountId = "accountId";
        Account account = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(5000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();
        Account accountUpdated = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(10000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();

        CreditDTO creditDTO = new CreditDTO(accountId, "description", BigDecimal.valueOf(5000), null);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(accountUpdated);
        when(operationRepository.save(any())).thenReturn(
                Operation.builder().id("operationId")
                        .date(LocalDateTime.now()).type(OperationType.DEBIT).amount(BigDecimal.valueOf(5000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR).description("description").account(accountUpdated)
                        .build()
        );
        CreditDTO result = service.credit(creditDTO);
        assertNotNull(result);
        assertEquals(result.accountId(), accountId);
        assertEquals(result.description(), creditDTO.description());
        assertEquals(result.amount(), creditDTO.amount());
        verify(accountRepository, times(1)).save(any());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void debit() throws AccountNotActivatedException, AccountNotFoundException, BalanceNotSufficientException {
        LocalDateTime dateTime = LocalDateTime.now();
        String accountId = "accountId";
        Account account = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(10000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();
        Account accountUpdated = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(5000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(dateTime).lastUpdate(dateTime).operations(new ArrayList<>())
                .build();

        DebitDTO debitDTO = new DebitDTO(accountId, "description", BigDecimal.valueOf(5000), null);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(accountUpdated);
        when(operationRepository.save(any())).thenReturn(
                Operation.builder().id("operationId")
                        .date(LocalDateTime.now()).type(OperationType.CREDIT).amount(BigDecimal.valueOf(5000))
                        .currency(com.github.ankurpathak.accountservice.enums.Currency.EUR).description("description").account(accountUpdated)
                        .build()
        );
        DebitDTO result = service.debit(debitDTO);
        assertNotNull(result);
        assertEquals(result.accountId(), accountId);
        assertEquals(result.description(), debitDTO.description());
        assertEquals(result.amount(), debitDTO.amount());
        verify(accountRepository, times(1)).save(any());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void getById() throws OperationNotFoundException {
        String id = "operationId";
        Operation operation = Operation.builder().id(id)
                .date(LocalDateTime.now()).type(OperationType.CREDIT).amount(BigDecimal.valueOf(5000))
                .currency(com.github.ankurpathak.accountservice.enums.Currency.EUR).description("description")
                .build();
        when(operationRepository.findById(id)).thenReturn(Optional.of(operation));
        when(mappers.fromOperation(operation)).thenReturn(
                new OperationDTO(id, operation.getDate(), operation.getType(), operation.getAmount(), operation.getCurrency(), operation.getDescription(), "accountId")
        );
        OperationDTO response = service.getById(id);
        assertEquals(response.id(), operation.getId());
    }

    @Test
    void getHistory() throws AccountNotFoundException {
        String accountId = "accountId";
        int size = 5;
        int page = 0;
        Account account = Account.builder().id(accountId).customerId("customerId").balance(BigDecimal.valueOf(10000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR)
                .status(AccountStatus.ACTIVATED).creation(LocalDateTime.now()).lastUpdate(LocalDateTime.now()).operations(new ArrayList<>())
                .build();


        Operation operation1 = Operation.builder().id("id1").date(LocalDateTime.now()).type(OperationType.CREDIT)
                .amount(BigDecimal.valueOf(5000)).currency(com.github.ankurpathak.accountservice.enums.Currency.EUR).description("description")
                .build();
        Operation operation2 = Operation.builder().id("id2").date(LocalDateTime.now()).type(OperationType.CREDIT)
                .amount(BigDecimal.valueOf(6000)).currency(Currency.EUR).description("description")
                .build();

        List<Operation> operationList = List.of(operation1, operation2);
        Page<Operation> operationPage = new PageImpl<>(operationList);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(operationRepository.findByAccountIdOrderByDateDesc(eq(accountId), any(PageRequest.class))).thenReturn(operationPage);
        when(mappers.fromOperation(any(Operation.class))).thenReturn(
                new OperationDTO("id1", operation1.getDate(), operation1.getType(), operation1.getAmount(), operation1.getCurrency(), operation1.getDescription(), "accountId")
        );

        HistoryDTO result = service.getHistory(accountId, page, size);
        assertNotNull(result);
        assertEquals(result.accountId(), accountId);
        assertEquals(result.customerId(), account.getCustomerId());
        assertEquals(result.operations().size(), operationList.size());
    }
}