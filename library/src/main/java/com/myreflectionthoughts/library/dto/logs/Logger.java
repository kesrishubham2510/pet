package com.myreflectionthoughts.library.dto.logs;

import com.myreflectionthoughts.library.contract.ILogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;

@Slf4j
public class Logger implements ILogger {

    // customLog is to be sent to kafka for storage
    @Override
    public void log(CustomLog customLog, LogLevel logLevel) {
        if(logLevel.equals(LogLevel.INFO)){
            log.info(customLog.getMessage());
        }else if(logLevel.equals(LogLevel.DEBUG)){
            log.debug(customLog.getMessage());
        }else if(logLevel.equals(LogLevel.ERROR)){
            log.info(customLog.getMessage());
        }
    }
}
