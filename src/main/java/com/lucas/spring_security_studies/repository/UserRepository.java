package com.lucas.spring_security_studies.repository;

import com.lucas.spring_security_studies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
}
