package com.ideas2it.employeemanagement.exception;

public class CustomException extends Exception {
    String exceptionMessage;
 
    public CustomException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}