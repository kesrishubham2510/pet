package com.myreflectionthoughts.library.contract;

import java.util.logging.Logger;

public interface IExitLogger {
    // method exit logs should be logged at info level
    void logExit(Logger logger, String message);
}
