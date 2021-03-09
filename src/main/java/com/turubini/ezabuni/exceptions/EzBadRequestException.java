package com.turubini.ezabuni.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EzBadRequestException extends RuntimeException{

    public EzBadRequestException (String message) {super(message);}
}
