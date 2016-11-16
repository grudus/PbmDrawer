package com.grudus.pbmdrawer.exceptions;


import java.io.IOException;

public class PbmImageException extends IOException {
    public PbmImageException() {
        super("Not valid pbm image");
    }

    public PbmImageException(String s) {
        super(s);
    }

    public PbmImageException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PbmImageException(Throwable throwable) {
        super(throwable);
    }
}
