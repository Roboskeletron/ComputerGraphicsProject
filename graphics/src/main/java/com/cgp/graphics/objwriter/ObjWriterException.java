package com.cgp.graphics.objwriter;

public class ObjWriterException extends RuntimeException {
    public ObjWriterException(String errorMessage) {
        super("Error in ObjWriter: " + errorMessage);
    }
}
