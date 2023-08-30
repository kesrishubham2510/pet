package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class AddPetDTO {
    private String name;
    // to map a pet With master
    private String masterId;
    private String category;
    private String gender;
    private double age;
}
