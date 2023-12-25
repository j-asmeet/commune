/**
 * This is an abstract class representing a custom exception for API-related errors.
 * Author: Kruti Panchal
 */
package com.group6.commune.Exceptions;

public abstract class ApiException extends RuntimeException {

    /**
     * Constructs a new ApiException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public ApiException(String message) {
        super(message);
    }
}
