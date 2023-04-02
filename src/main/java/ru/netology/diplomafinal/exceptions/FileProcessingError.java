package ru.netology.diplomafinal.exceptions;

public class FileProcessingError extends RuntimeException{
    public FileProcessingError(String msg) {
        super(msg);
    }
}
