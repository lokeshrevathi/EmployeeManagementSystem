package com.ideas2it.ems.exception;

public class AlreadyExistException extends Exception {
    String exceptionMessage;
 
    public AlreadyExistException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}