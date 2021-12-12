package com.onurcelik.readingisgood.core.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDTO<T> {

    private boolean success;
    private HttpStatus httpStatus;
    private String errorMessage;
    private T data;

    public ResponseDTO(HttpStatus httpStatus, T data) {
        this.success = true;
        this.httpStatus = httpStatus;
        this.errorMessage = "";
        this.data = data;
    }
}
