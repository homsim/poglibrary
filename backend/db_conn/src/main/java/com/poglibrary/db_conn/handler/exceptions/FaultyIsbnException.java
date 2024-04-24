package com.poglibrary.db_conn.handler.exceptions;

public class FaultyIsbnException extends RuntimeException {
    public FaultyIsbnException() {
    }

    public FaultyIsbnException(String message) {
        super(message);
    }
}