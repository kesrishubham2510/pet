package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

@Data
public class MasterDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private double age;
    private String address;
}