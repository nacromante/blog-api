package com.framework.blogapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse<T> {

    private List<T> errors;

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }
}
