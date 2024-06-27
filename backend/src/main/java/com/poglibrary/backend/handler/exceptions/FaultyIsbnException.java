package com.poglibrary.backend.handler.exceptions;

public class FaultyIsbnException extends RuntimeException {
    public FaultyIsbnException() {
    }

    public FaultyIsbnException(String message) {
        super(message);
    }
}