package com.telran.phonebookapi.dto;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public class ErrorDto {
    public String message;
}
