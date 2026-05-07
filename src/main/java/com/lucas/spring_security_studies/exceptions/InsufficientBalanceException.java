package com.lucas.spring_security_studies.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
        super("Insufficient balance");
    }
}
