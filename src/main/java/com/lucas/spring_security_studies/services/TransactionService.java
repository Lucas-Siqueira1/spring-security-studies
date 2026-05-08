package com.lucas.spring_security_studies.services;
import com.lucas.spring_security_studies.dto.TransactionRequestDto;
import com.lucas.spring_security_studies.dto.TransactionResponseDto;
import com.lucas.spring_security_studies.entities.Account;
import com.lucas.spring_security_studies.entities.Transaction;
import com.lucas.spring_security_studies.exceptions.ResourceNotFoundException;
import com.lucas.spring_security_studies.repository.AccountRepository;
import com.lucas.spring_security_studies.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    public List<TransactionResponseDto> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(t -> new TransactionResponseDto(t.getId(), t.getSenderAccount().getId(), t.getReceiverAccount().getId(),
                        t.getValue(), t.getMoment()))
                .toList();
    }

    public TransactionResponseDto findTransactionById(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new TransactionResponseDto(transaction.getId(), transaction.getSenderAccount().getId(), transaction.getReceiverAccount().getId(),
                transaction.getValue(), transaction.getMoment());
    }

    @Transactional
    public TransactionResponseDto insert(TransactionRequestDto dto) {
        Transaction transaction = new Transaction();
        Account receiverAccount = accountRepository.findById(dto.getReceiveAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getReceiveAccountId()));
        Account senderAccount = accountRepository.findById(dto.getSenderAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getSenderAccountId()));
        transaction.setReceiverAccount(receiverAccount);
        transaction.setSenderAccount(senderAccount);
        transaction.setValue(dto.getValue());
        transaction.setMoment(Instant.now());
        receiverAccount.deposit(dto.getValue());
        senderAccount.withdraw(dto.getValue());
        accountRepository.save(receiverAccount);
        accountRepository.save(senderAccount);
        Transaction saved = transactionRepository.save(transaction);
        return new TransactionResponseDto(saved.getId(), saved.getSenderAccount().getId(), saved.getReceiverAccount().getId(),
                saved.getValue(), saved.getMoment());
    }

}
