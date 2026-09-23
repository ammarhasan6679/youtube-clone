package com.osb.youtube.exception;

public class VideoAlreadyExistsInPlaylistException extends RuntimeException{
    public VideoAlreadyExistsInPlaylistException(String message) {
        super(message);
    }
}
