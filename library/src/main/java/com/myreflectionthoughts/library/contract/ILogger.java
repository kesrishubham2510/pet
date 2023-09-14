package com.myreflectionthoughts.library.contract;

import com.myreflectionthoughts.library.dto.logs.CustomLog;
import org.springframework.boot.logging.LogLevel;

public interface ILogger {
    void log(CustomLog customLog, LogLevel logLevel);
}
