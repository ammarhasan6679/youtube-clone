package com.osb.youtube.exception;

public class PlaylistNotFoundException extends RuntimeException{
    public PlaylistNotFoundException(String message) {
        super(message);
    }
}
