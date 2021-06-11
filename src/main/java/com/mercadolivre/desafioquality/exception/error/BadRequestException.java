package com.mercadolivre.desafioquality.exception.error;


public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
