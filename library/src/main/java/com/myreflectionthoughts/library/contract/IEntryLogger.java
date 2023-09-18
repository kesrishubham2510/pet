package com.myreflectionthoughts.library.contract;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface IEntryLogger {
    // method entry logs should be logged at info level
    void logEntry(Logger logger, String message);
    void logEntry(Logger logger, String message, Level logLevel);
}
