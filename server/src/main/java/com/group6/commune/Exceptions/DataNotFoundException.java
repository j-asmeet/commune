/**
 * This exception is thrown when data is not found in the system.
 * Author: Kruti Panchal
 */
package com.group6.commune.Exceptions;

public class DataNotFoundException extends RuntimeException {

    /**
     * Constructs a new DataNotFoundException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
