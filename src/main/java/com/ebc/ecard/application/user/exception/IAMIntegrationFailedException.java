package com.ebc.ecard.application.user.exception;

public class IAMIntegrationFailedException extends RuntimeException {

    public IAMIntegrationFailedException() {
    }

    public IAMIntegrationFailedException(String message) {
        super(message);
    }

    public IAMIntegrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IAMIntegrationFailedException(Throwable cause) {
        super(cause);
    }

    public IAMIntegrationFailedException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
