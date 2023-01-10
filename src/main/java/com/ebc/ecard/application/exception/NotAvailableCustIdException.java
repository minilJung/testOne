package com.ebc.ecard.application.exception;

public class NotAvailableCustIdException extends RuntimeException {

    public NotAvailableCustIdException() {
        super("custId가 유효하지 않습니다.");
    }
}
