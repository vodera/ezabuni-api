package com.turubini.ezabuni.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EzResourceNotFoundException extends RuntimeException {

    public EzResourceNotFoundException (String message) {
        super (message);
    }
}
