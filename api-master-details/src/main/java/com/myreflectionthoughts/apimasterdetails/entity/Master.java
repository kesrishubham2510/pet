package com.myreflectionthoughts.apimasterdetails.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Master {
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private double age;
    private String address;
}
