package com.ebc.ecard.application.ecard.exception;

public class PreviewImageUpdateFailedException extends RuntimeException {
    public PreviewImageUpdateFailedException() {}

    public PreviewImageUpdateFailedException(String message) {
        super(message);
    }

    public PreviewImageUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreviewImageUpdateFailedException(Throwable cause) {
        super(cause);
    }

    public PreviewImageUpdateFailedException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
