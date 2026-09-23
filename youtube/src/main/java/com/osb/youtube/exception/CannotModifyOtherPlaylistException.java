package com.osb.youtube.exception;

public class CannotModifyOtherPlaylistException extends RuntimeException{
    public CannotModifyOtherPlaylistException(String message) {
        super(message);
    }
}
