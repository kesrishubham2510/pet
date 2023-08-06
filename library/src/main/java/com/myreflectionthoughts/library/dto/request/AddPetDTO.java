package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

@Data
public class AddPetDTO {
    private String name;
    private String master;
    private String category;
    private String gender;
    private double age;
}
