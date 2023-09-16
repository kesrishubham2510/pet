package com.myreflectionthoughts.library.contract;

import com.myreflectionthoughts.library.dto.logs.CustomLog;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ILogger {
    void log(Logger logger, CustomLog customLog, Level logLevel);
}
