package com.example.demo.util;

import org.slf4j.Logger;

public class LogUtil {

    public static void logEntry(final String method, final Logger logger) {
        logger.debug("{} | Entry", method);
    }

    public static void logExit(final String method, final Logger logger) {
        logger.debug("{} | Exit", method);
    }

    public static void logDebug(final String method, final String message, final Logger logger) {
        logger.debug("{} | Message: {}", method, message);
    }

    public static void logInfo(final String method, final String message, final Logger logger) {
        logger.info("{} | Message: {}", method, message);
    }

    public static void logWarn(final String method, final String message, final Logger logger) {
        logger.warn("{} | Message: {}", method, message);
    }

    public static void logWarn(final String method, final String message, final Throwable throwable,
        final Logger logger) {
        logger.warn("{} | Message: {}", method, message, throwable);
    }

    public static void logError(final String method, final String message, final Logger logger) {
        logger.error("{} | Message: {}", method, message);
    }

    public static void logError(final String method, final String message, final Throwable throwable,
        final Logger logger) {
        logger.error("{} | Message: {}", method, message, throwable);
    }
}
