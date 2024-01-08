package com.example.holaluz.exception;

import com.example.holaluz.config.ErrorCode;

public class FileConverterException extends GenericException {

    public FileConverterException(ErrorCode message) {
        super(message);
    }


}