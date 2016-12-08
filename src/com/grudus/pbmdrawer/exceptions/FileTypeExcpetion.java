package com.grudus.pbmdrawer.exceptions;


import java.io.IOException;

public class FileTypeExcpetion extends IOException {
    public FileTypeExcpetion() {
        super();
    }

    public FileTypeExcpetion(String message) {
        super(message);
    }

    public FileTypeExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeExcpetion(Throwable cause) {
        super(cause);
    }
}
