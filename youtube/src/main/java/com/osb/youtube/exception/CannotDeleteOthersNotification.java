package com.osb.youtube.exception;

public class CannotDeleteOthersNotification extends RuntimeException{
    public CannotDeleteOthersNotification(String message) {
        super(message);
    }
}
