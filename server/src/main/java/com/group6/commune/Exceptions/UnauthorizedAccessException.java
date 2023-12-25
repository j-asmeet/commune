/**
 * This exception is thrown when a user attempts to access a resource without proper authorization.
 * Author: Jasmeet Singh
 */
package com.group6.commune.Exceptions;

public class UnauthorizedAccessException extends RuntimeException {

    /**
     * Constructs a new UnauthorizedAccessException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
