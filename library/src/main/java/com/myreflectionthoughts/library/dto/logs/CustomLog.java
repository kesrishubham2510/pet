package com.myreflectionthoughts.library.dto.logs;

import lombok.ToString;


@ToString
public class CustomLog {


    // mongoDB generated id
    private final String id;

    // uuid to uniquely identify the log
    protected final String log;
    protected final String useCase;
    protected final String timestamp;
    protected final String methodName;
    protected final String endpoint;
    protected final String requestType;
    protected String message;
    protected final String pointOfIncident; // api where this was logged;

    public CustomLog(String id, String log, String useCase, String timestamp, String methodName, String endpoint, String requestType, String message, String pointOfIncident) {
        this.id = id;
        this.log = log;
        this.useCase = useCase;
        this.timestamp = timestamp;
        this.methodName = methodName;
        this.endpoint = endpoint;
        this.requestType = requestType;
        this.message = message;
        this.pointOfIncident = pointOfIncident;
    }

    public CustomLog(String log, String useCase, String timestamp, String methodName, String endpoint, String requestType, String message, String pointOfIncident) {
        this(null,log,useCase,timestamp,methodName,endpoint,requestType,message,pointOfIncident);
    }

    public CustomLog(CustomLog customLog){
        this(customLog.getId(),customLog.getLog(), customLog.getUseCase(), customLog.getTimestamp(), customLog.getMethodName(), customLog.getEndpoint(), customLog.getRequestType(), customLog.getMessage(), customLog.getPointOfIncident());
    }

    public String getId() {
        return id;
    }
    public String getLog() {
        return log;
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

    public void setMessage(String message){
        this.message = message;
    }
}
