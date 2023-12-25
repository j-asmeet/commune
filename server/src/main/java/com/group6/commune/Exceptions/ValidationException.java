/**
 * This exception is thrown when a validation error occurs during input data validation.
 * It includes a map of field names and corresponding error messages.
 * Author: Kruti Panchal
 */
package com.group6.commune.Exceptions;

import java.util.Map;

public class ValidationException extends ApiException {

    private Map<String, String> errors;

    /**
     * Constructs a new ValidationException with the specified error message and validation errors.
     *
     * @param message The error message for the exception.
     * @param errors  A map of field names and corresponding error messages.
     */
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    /**
     * Get the map of field names and corresponding error messages.
     *
     * @return The map of errors.
     */
    public Map<String, String> getErrors() {
        return errors;
    }
}
