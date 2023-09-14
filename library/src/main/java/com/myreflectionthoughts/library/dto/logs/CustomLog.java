package com.myreflectionthoughts.library.dto.logs;

import lombok.ToString;

@ToString
public class CustomLog {
    protected final String logId;
    protected final String useCase;
    protected final String timestamp;
    protected final String methodName;
    protected final String endpoint;
    protected final String requestType;
    protected final String message;
    protected final String pointOfIncident; // api where this was logged;


    public CustomLog(String logId, String useCase, String timestamp, String methodName, String endpoint, String requestType, String message, String pointOfIncident) {
        this.logId = logId;
        this.useCase = useCase;
        this.timestamp = timestamp;
        this.methodName = methodName;
        this.endpoint = endpoint;
        this.requestType = requestType;
        this.message = message;
        this.pointOfIncident = pointOfIncident;
    }

    public CustomLog(CustomLog customLog){
        this(customLog.getLogId(), customLog.getUseCase(), customLog.getTimestamp(), customLog.getMethodName(), customLog.getEndpoint(), customLog.getRequestType(), customLog.getMessage(), customLog.getPointOfIncident());
    }

    public String getLogId() {
        return logId;
    }

    public String getUseCase() {
        return useCase;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getMessage() {
        return message;
    }

    public String getPointOfIncident() {
        return pointOfIncident;
    }
}
