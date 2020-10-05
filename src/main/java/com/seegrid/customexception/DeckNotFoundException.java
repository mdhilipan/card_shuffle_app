package com.seegrid.customexception;

public class DeckNotFoundException extends Exception{
    public DeckNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
