package com.example.todolist;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int statusCode;

    public ErrorResponse() {
        this.statusCode = 500;
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.statusCode = 500;
    }
}
