package com.ebc.ecard.application.exception;

public class AlreadyRegisteredFpException extends RuntimeException {

    public AlreadyRegisteredFpException() {
        super("이미 가입된 FP입니다.");
    }
}
