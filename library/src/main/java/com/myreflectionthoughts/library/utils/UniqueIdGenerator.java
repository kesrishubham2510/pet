package com.myreflectionthoughts.library.utils;

import java.util.UUID;
import java.util.logging.Logger;

public class UniqueIdGenerator {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(UniqueIdGenerator.class.getName());
    }
    public UniqueIdGenerator(){}

    public static String generateSpanID(){
        return UUID.randomUUID().toString();
    }

    public static String generateTraceId(){
        return UUID.randomUUID().toString();
    }
}
