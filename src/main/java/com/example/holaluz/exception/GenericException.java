package com.example.holaluz.exception;


import com.example.holaluz.config.ErrorCode;

public class GenericException extends RuntimeException {
    public GenericException(ErrorCode message) {
        super(message.getReasonPhrase());
    }
}
