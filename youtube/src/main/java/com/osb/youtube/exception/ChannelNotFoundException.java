package com.osb.youtube.exception;

public class ChannelNotFoundException extends RuntimeException{
    public ChannelNotFoundException(String message) {
        super(message);
    }
}
