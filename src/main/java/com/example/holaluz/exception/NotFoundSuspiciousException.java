package com.example.holaluz.exception;

import com.example.holaluz.config.ErrorCode;

public class NotFoundSuspiciousException extends GenericException {

    public NotFoundSuspiciousException(ErrorCode message) {
        super(message);
    }


}