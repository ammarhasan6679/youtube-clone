package com.osb.youtube.exception;

public class CannotSubscribeToOwnChannelException extends RuntimeException{
    public CannotSubscribeToOwnChannelException(String message) {
        super(message);
    }
}
