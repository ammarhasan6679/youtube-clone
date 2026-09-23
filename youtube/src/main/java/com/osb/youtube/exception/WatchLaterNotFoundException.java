package com.osb.youtube.exception;

public class WatchLaterNotFoundException extends RuntimeException{
    public WatchLaterNotFoundException(String message) {
        super(message);
    }
}
