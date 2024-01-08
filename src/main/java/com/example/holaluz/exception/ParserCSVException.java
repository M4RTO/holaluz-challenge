package com.example.holaluz.exception;

import com.example.holaluz.config.ErrorCode;

public class ParserCSVException extends GenericException {

    public ParserCSVException(ErrorCode message) {
        super(message);
    }


}