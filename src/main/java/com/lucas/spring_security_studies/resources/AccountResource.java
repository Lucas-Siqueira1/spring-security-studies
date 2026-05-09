package com.lucas.spring_security_studies.resources;

import com.lucas.spring_security_studies.dto.AccountRequestDto;
import com.lucas.spring_security_studies.dto.AccountResponseDto;
import com.lucas.spring_security_studies.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> findAllAccounts() {
        List<AccountResponseDto> obj = accountService.findAllAccounts();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> findAccountById(@PathVariable Integer id) {
        AccountResponseDto obj = accountService.findAccountById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> insert(@RequestBody AccountRequestDto obj) {
        AccountResponseDto saved = accountService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
