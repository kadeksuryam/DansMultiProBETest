package com.surya.dansbetest.exception;

import com.surya.dansbetest.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        ErrorResponse.ErrorBody errBody = ErrorResponse.ErrorBody
                .builder()
                .message(ex.getMessage())
                .build();

        ErrorResponse error = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid Arguments")
                .errors(Arrays.asList(errBody))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse.ErrorBody errBody = ErrorResponse.ErrorBody
                .builder()
                .message(ex.getMessage())
                .build();

        ErrorResponse error = ErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal Server Error")
                .errors(Arrays.asList(errBody))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

