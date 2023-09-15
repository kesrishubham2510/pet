package com.myreflectionthoughts.library.dto.logs;

import lombok.Getter;

@Getter
public class ExceptionLog extends CustomLog {
    private final String exception;
    private final String exceptionMessage;

    public ExceptionLog(CustomLog customLog, String exception, String exceptionMessage) {
        super(customLog);
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString(){
        String log = super.toString();
        return new StringBuilder(log.substring(0, log.length()-1))
                .append(", exception:- ")
                .append(this.exception)
                .append(", exceptionMessage:- ")
                .append(this.exceptionMessage)
                .append(" )").toString();
    }
}
