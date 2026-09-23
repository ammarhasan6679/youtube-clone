package com.osb.youtube.exception;

public class CannotJoinYourOwnChannelException extends RuntimeException{
    public CannotJoinYourOwnChannelException(String message) {
        super(message);
    }
}
