package com.bridgelabz.fundoo_notes.exception;



public class UnauthorizedException
        extends RuntimeException {

    public UnauthorizedException(
            String message) {
        super(message);
    }
}