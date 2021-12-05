package dev.kopka.shiptracker.exception.exceptions;

public class ApiException extends Exception{
    public ApiException(String message) {
        super(message);
    }
}
