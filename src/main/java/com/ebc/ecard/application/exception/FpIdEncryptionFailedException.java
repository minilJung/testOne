package com.ebc.ecard.application.exception;

public class FpIdEncryptionFailedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "FP ID를 암호화하지 못했습니다.";

    public FpIdEncryptionFailedException() {
        super(DEFAULT_MESSAGE);
    }

    public FpIdEncryptionFailedException(String message) {
        super(DEFAULT_MESSAGE + message);
    }

    public FpIdEncryptionFailedException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + message, cause);
    }

    public FpIdEncryptionFailedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public FpIdEncryptionFailedException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
