package com.myreflectionthoughts.apimasterdetails.core.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Master {
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private double age;
    private String address;
    @Field
    private boolean markForDelete=false;
}
