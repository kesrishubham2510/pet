package com.myreflectionthoughts.apipetdetails.entity;

import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("pets")
@Data
public class Pet {
    private String id;
    private String name;
    private String master;
    private Category category;
    private Gender gender;
    private ClinicCardStatus clinicCardStatus;
    private double age;
}
