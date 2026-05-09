package com.lucas.spring_security_studies.resources;

import com.lucas.spring_security_studies.dto.TransactionRequestDto;
import com.lucas.spring_security_studies.dto.TransactionResponseDto;
import com.lucas.spring_security_studies.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> findAllTransactions() {
        List<TransactionResponseDto> obj = transactionService.findAllTransactions();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> findTransactionById(@PathVariable Integer id) {
        TransactionResponseDto obj = transactionService.findTransactionById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> insert(@RequestBody TransactionRequestDto obj) {
        TransactionResponseDto saved = transactionService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }
}
