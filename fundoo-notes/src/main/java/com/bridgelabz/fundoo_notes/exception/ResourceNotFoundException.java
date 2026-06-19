package com.bridgelabz.fundoo_notes.exception;



public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {
        super(message);
    }
}