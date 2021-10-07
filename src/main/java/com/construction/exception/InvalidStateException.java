package com.construction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;

public class InvalidStateException extends ResponseStatusException {
    public InvalidStateException(@NonNull Type clazz, @NonNull Long id) {
        super(HttpStatus.FORBIDDEN, String.format("Entity:%s with ID:%s is not in valid state", clazz.getTypeName(), id));
    }
}
