package com.minesweeper.api.exceptions;

public class InvalidGameStatusException extends RuntimeException {

    public InvalidGameStatusException(String message) {
        super(message);
    }

    public InvalidGameStatusException(Throwable cause) {
        super(cause);
    }
}
