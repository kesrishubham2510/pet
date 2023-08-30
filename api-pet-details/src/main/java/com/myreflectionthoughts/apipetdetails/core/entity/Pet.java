package com.myreflectionthoughts.apipetdetails.core.entity;

import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("pets")
@Data
public class Pet {
    private String id;
    // to map a pet With master
    private String master;
    private String name;
    private Category category;
    private Gender gender;
    private ClinicCardStatus clinicCardStatus;
    private double age;
}
