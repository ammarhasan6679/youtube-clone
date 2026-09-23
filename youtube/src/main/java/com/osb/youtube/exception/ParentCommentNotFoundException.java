package com.osb.youtube.exception;

public class ParentCommentNotFoundException extends RuntimeException{
    public ParentCommentNotFoundException(String message) {
        super(message);
    }
}
