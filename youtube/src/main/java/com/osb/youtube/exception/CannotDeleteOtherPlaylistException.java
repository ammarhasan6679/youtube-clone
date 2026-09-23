package com.osb.youtube.exception;

public class CannotDeleteOtherPlaylistException extends RuntimeException{
    public CannotDeleteOtherPlaylistException(String message) {
        super(message);
    }
}
