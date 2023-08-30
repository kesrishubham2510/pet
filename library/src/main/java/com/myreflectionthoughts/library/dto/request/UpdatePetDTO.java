package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class UpdatePetDTO {
    private String id;
    // to map a pet With master
    private String master;
    private String name;
    private String category;
    private String gender;
    private String clinicCardStatus;
    private double age;
}
