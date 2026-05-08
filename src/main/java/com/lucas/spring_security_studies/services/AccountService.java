package com.lucas.spring_security_studies.services;
import com.lucas.spring_security_studies.dto.AccountRequestDto;
import com.lucas.spring_security_studies.dto.AccountResponseDto;
import com.lucas.spring_security_studies.entities.Account;
import com.lucas.spring_security_studies.exceptions.ResourceNotFoundException;
import com.lucas.spring_security_studies.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<AccountResponseDto> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(t -> new AccountResponseDto(t.getId(), t.getBalance(), t.getAccountNumber()))
                .toList();
    }

    public AccountResponseDto findAccountById(Integer id) {
        Account obj = accountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
        return new AccountResponseDto(obj.getId(), obj.getBalance(), obj.getAccountNumber());
    }

    public AccountResponseDto insert(AccountRequestDto dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(0.0);
        Account saved = accountRepository.save(account);
        return new AccountResponseDto(saved.getId(), saved.getBalance(), saved.getAccountNumber());
    }

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }
}
