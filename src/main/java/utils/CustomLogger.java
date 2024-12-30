package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    private final Logger logger;

    /**
     * Constructor to initialize the logger for a specific class.
     *
     * @param clazz the class for which logging is being set up.
     */
    public CustomLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void pass(String message) {
        logger.info(message);
    }

    /**
     * Logs an info message.
     *
     * @param message the message to log.
     */
    public void infoWithoutReport(String message) {
        logger.info(message);
    }

    public void infoWithReport(String message) {
        logger.info(message);
    }
    /**
     * Logs a debug message.
     *
     * @param message the message to log.
     */
    public void debugWithReport(String message) {
        logger.debug(message);
    }

    public void debugWithoutReport(String message) {
        logger.debug(message);
    }
    /**
     * Logs a warning message.
     *
     * @param message the message to log.
     */
    public void warn(String message) {
        logger.warn(message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log.
     * @param throwable the exception to log.
     */
    public void logErrorWithoutReport(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void logErrorWithReport(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    /**
     * Logs an error message without an exception.
     *
     * @param message the message to log.
     */
    public void logErrorWithoutReport(String message) {
        logger.error(message);
    }

    public void logErrorWithReport(String message) {
        logger.error(message);
    }
}
