package com.osb.youtube.exception;

public class CannotModifyOthersNotification extends RuntimeException{
    public CannotModifyOthersNotification(String message) {
        super(message);
    }
}
