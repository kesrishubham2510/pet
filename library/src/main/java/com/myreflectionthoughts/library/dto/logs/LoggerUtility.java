package com.myreflectionthoughts.library.dto.logs;

import com.myreflectionthoughts.library.contract.IEntryLogger;
import com.myreflectionthoughts.library.contract.IExitLogger;
import com.myreflectionthoughts.library.contract.ILogger;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtility implements ILogger, IEntryLogger, IExitLogger {

    // customLog is to be sent to kafka for storage

    @Override
    public void log(Logger logger, CustomLog customLog, Level logLevel) {
        logger.log(Objects.requireNonNullElse(logLevel, Level.WARNING), customLog.message);
    }

    @Override
    public void logEntry(Logger logger, String message) {
        logger.log(Level.INFO, message);
    }

    @Override
    public void logExit(Logger logger, String message) {
        logger.log(Level.INFO, message);
    }

    public void log(Logger logger, String message, Level logLevel){
        logger.log(Objects.requireNonNullElse(logLevel, Level.WARNING), message);
    }
}
