package com.example.holaluz.exception;

import com.example.holaluz.config.ErrorCode;

public class ParserXMLException extends GenericException {

    public ParserXMLException(ErrorCode message) {
        super(message);
    }


}