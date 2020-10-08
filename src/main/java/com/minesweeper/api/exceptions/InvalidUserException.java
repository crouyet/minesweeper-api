package com.minesweeper.api.exceptions;

public class InvalidUserException extends IllegalArgumentException {

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(Throwable cause) {
        super(cause);
    }
}
