package com.minesweeper.api.exceptions;

public class InvalidCellException extends IllegalArgumentException {

    public InvalidCellException(String message) {
        super(message);
    }

    public InvalidCellException(Throwable cause) {
        super(cause);
    }
}
