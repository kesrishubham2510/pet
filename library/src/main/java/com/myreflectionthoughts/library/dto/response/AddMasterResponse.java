package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

@Data
public class AddMasterResponse {
    private String id;
    private String name;
    private String email;
    private String password;
    private double age;
    private String address;
}