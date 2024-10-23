package com.enigma.teamtaskmanager.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String resourceType, final Long resourceId) {
        super(resourceType + " with id = " + resourceId + " not found");
    }
}
