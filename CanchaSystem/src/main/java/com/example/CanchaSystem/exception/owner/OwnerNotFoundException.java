package com.example.CanchaSystem.exception.owner;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class OwnerNotFoundException extends Exception {
    public OwnerNotFoundException(String message) {
        super(message);
    }
}
