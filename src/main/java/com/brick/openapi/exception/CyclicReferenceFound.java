package com.brick.openapi.exception;

public class CyclicReferenceFound extends Exception {
    public CyclicReferenceFound(String message) {
        super(message);
    }
}
