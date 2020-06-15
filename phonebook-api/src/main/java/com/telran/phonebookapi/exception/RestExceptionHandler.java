package com.telran.phonebookapi.exception;

import com.telran.phonebookapi.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<Object> handleBadRequest(AbstractException e,
                                                    WebRequest request) {

        return handleExceptionInternal(
                e,
                new ErrorDto(e.getMessage()),
                new HttpHeaders(),
                e.getStatus(),
                request);
    }
}
