package com.osb.youtube.exception;

public class VideoNotFoundInPlaylistException extends RuntimeException{
    public VideoNotFoundInPlaylistException(String messsage) {
        super(messsage);
    }
}
