package com.myreflectionthoughts.library.contract;

import java.util.logging.Logger;

public interface IEntryLogger {
    // method entry logs should be logged at info level
    void logEntry(Logger log, String message);
}
