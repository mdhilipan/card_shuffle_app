package com.seegrid.customexception;

public class ServerStartupException extends Exception{
    public ServerStartupException(String errorMessage) {
        super(errorMessage);
    }
}
