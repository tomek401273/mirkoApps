package com.tgrajkowski.exception;

public class MicroserviceUnavailableException extends RuntimeException {
    public MicroserviceUnavailableException(String message) {
        super(message);
    }
}
