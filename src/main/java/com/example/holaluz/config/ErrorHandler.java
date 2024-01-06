package com.example.holaluz.config;

import com.example.holaluz.exception.NotFileExtensionException;
import com.example.holaluz.exception.ParserCSVException;
import com.example.holaluz.exception.ParserXMLException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final ZoneId UTC_ZONE = ZoneOffset.UTC;


    @ExceptionHandler(NotFileExtensionException.class)
    private ResponseEntity<ApiErrorResponse> handleNotFileExtension(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(UTC_ZONE),
                ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);


    }


    @ExceptionHandler(ParserXMLException.class)
    private ResponseEntity<ApiErrorResponse> handleXMLParserException(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(UTC_ZONE),
                ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ParserCSVException.class)
    private ResponseEntity<ApiErrorResponse> handleCSVParserException(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);

        ApiErrorResponse apiErrorResponse =  new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(UTC_ZONE),
                ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipartException.class)
    private ResponseEntity<ApiErrorResponse> handleMultipartException(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        ApiErrorResponse apiErrorResponse =  new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(UTC_ZONE),
                ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }


    @Builder
    @NonNull
    @lombok.Value
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    private static class ApiErrorResponse {

        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";
        HttpStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        LocalDateTime timestamp;
        String message;
    }
}

