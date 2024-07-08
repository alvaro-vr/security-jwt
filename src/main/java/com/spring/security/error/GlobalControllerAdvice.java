package com.spring.security.error;

import com.spring.security.auth.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ApiError error = new ApiError(statusCode.value(), ex.getMessage(), LocalDate.now());
        return ResponseEntity.status(statusCode).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> userAlreadyExists(UserAlreadyExistsException ex){
        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDate.now());
        return ResponseEntity.status(HttpStatus.valueOf(apiError.getStatusCode())).body(apiError);
    }

}
