package com.osb.youtube.exception;

public class ReactionNotFoundException extends RuntimeException{
    public ReactionNotFoundException(String message) {
        super(message);
    }
}
