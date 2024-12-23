package utils;

import org.apache.logging.log4j.LogManager;

import java.util.logging.Level;

public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void error(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }
}
