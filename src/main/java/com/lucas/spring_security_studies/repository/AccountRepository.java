package com.lucas.spring_security_studies.repository;

import com.lucas.spring_security_studies.dto.AccountResponseDto;
import com.lucas.spring_security_studies.entities.Account;
import com.lucas.spring_security_studies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountOwner(User user);
}
