package com.lucas.spring_security_studies.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {

        super("Resource not found. Id " + id);
    }
}
