package com.enigma.teamtaskmanager.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(final String resourceType, final String fieldName, final String fieldValue) {
        super(resourceType + " with " + fieldName + " with value " + fieldValue + " already exists");
    }
}
