package com.osb.youtube.exception;

public class WatchLaterAlreadyExistsException extends RuntimeException{
    public WatchLaterAlreadyExistsException(String message) {
        super(message);
    }
}
