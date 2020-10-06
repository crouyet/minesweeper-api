package com.minesweeper.api.exceptions;

public class InvalidGameException extends RuntimeException {

    public InvalidGameException(String message) {
        super(message);
    }

    public InvalidGameException(Throwable cause) {
        super(cause);
    }
}
