package com.osb.youtube.exception;

public class SubscriptionNotFoundException extends RuntimeException{
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
