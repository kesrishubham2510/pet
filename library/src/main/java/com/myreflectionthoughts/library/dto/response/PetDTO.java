package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

@Data
public class PetDTO {
    private String id;
    private String name;
    private String master;
    private String category;
    private String gender;
    private String clinicCardStatus;
    private String clinicCardStatusMessage;
    private double age;
}