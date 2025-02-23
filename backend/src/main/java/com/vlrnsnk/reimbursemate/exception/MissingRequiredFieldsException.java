package com.vlrnsnk.reimbursemate.exception;

public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String message) {
        super(message);
    }
}
