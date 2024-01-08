package com.example.holaluz.exception;

import com.example.holaluz.config.ErrorCode;

public class NotFileExtensionException extends GenericException {

    public NotFileExtensionException(ErrorCode message) {
        super(message);
    }


}