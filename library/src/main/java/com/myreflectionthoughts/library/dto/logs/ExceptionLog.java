package com.myreflectionthoughts.library.dto.logs;

public class ExceptionLog extends CustomLog {
    private final String exception;

    public ExceptionLog(CustomLog customLog, String exception) {
        super(customLog);
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    @Override
    public String toString(){
        String log = super.toString();
        return new StringBuilder(log.substring(0, log.length()-1)).append(", exception:- ").append(this.exception).append(" )").toString();
    }
}
