package com.ebc.ecard.application.exception;

public class EmployeeCVNullException extends RuntimeException {

    public EmployeeCVNullException() {
        super("Employee CV 정보를 찾을 수 없습니다.");
    }
}
