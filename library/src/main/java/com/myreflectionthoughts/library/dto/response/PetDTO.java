package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

@Data
public class PetDTO {
    private String id;
    // to map a pet With master
    private String masterId;
    private String name;
    private String category;
    private String gender;
    private String clinicCardStatus;
    private String clinicCardStatusMessage;
    private double age;
}