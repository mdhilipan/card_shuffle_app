package com.seegrid.customexception;

public class NoMoreCardToDealException extends Exception{
    public NoMoreCardToDealException(String errorMessage) {
        super(errorMessage);
    }
}
