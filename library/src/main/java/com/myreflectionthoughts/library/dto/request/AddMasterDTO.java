package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class AddMasterDTO {
    private String name;
    private String email;
    private String password;
    private double age;
    private String address;
}
