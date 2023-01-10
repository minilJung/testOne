package com.ebc.ecard.application.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity not found : " + entityClass.getName());
    }
}
