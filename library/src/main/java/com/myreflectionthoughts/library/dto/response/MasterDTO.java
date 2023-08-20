package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

@Data
public class MasterDTO {
    private String id;
    private String name;
    private String email;
    private double age;
    private String address;
    private boolean markForDelete;
}