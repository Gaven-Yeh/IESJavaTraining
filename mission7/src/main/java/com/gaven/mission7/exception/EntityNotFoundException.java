package com.gaven.mission7.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, String type) {
        super("Could not find " + type + " " + id);
    }
}
