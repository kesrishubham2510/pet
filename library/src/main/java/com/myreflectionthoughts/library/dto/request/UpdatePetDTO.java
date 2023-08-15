package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class UpdatePetDTO {
    private String id;
    private String name;
    private String master;
    private String category;
    private String gender;
    private String clinicCardStatus;
    private double age;
}