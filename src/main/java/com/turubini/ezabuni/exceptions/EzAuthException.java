package com.turubini.ezabuni.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EzAuthException extends RuntimeException {

    public EzAuthException(String message) {
        super(message);
    }
}
