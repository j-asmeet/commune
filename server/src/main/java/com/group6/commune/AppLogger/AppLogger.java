/**
 * This class represents a custom application logger that provides a centralized way to obtain a logger instance.
 * The logger is implemented using the SLF4J library.
 * Author: Mehulkumar Bhunsadiya
 */
package com.group6.commune.AppLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    // The static logger instance for the class.
    private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    /**
     * Returns the logger instance for the class.
     *
     * @return The SLF4J logger instance that can be used for logging within the application.
     * @author Mehulkumar Bhunsadiya
     */
    public static Logger getLogger(){
        return logger;
    }
}
