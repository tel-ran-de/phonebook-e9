package com.telran.phonebookapi.exception;

import com.telran.phonebookapi.dto.TokenExceptionDto;
import com.telran.phonebookapi.dto.UserExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleBadRequest(UserAlreadyExistsException e,
                                                    WebRequest request) {

        return handleExceptionInternal(
                e,
                new UserExceptionDto(e.getEmail(), e.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(TokenNotFoundException e,
                                                    WebRequest request) {

        return handleExceptionInternal(
                e,
                new TokenExceptionDto(e.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }
}
