package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class UpdatePetDTO {
    private String id;
    // to map a pet With master
    private String masterId;
    private String name;
    private String master;
    private String category;
    private String gender;
    private String clinicCardStatus;
    private double age;
}
