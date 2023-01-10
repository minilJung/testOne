package com.ebc.ecard.application.exception;

public class UnexpectedFpNmcdDvsnCode extends RuntimeException {

    public UnexpectedFpNmcdDvsnCode() {
        super("fpNmcdDvsnCode가 유효하지 않습니다.");
    }
}
