package ru.netology.diplomafinal.exceptions;

public class InputDataError extends RuntimeException {
    public InputDataError(String msg) {
        super(msg);
    }
}
