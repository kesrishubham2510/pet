package com.myreflectionthoughts.apipetdetails.data;

import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;

public class TestDataGenerator {

    public static AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("male");
        addPetDTO.setName("pet-name");
        addPetDTO.setMaster("master");
        return addPetDTO;
    }

    public static Pet getPet() {
        Pet pet = new Pet();
        pet.setId("id");
        pet.setAge(1.0);
        pet.setCategory(Category.DOG);
        pet.setGender(Gender.FEMALE);
        pet.setName("pet-name");
        pet.setMaster("master");
        pet.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);
        return pet;
    }
}
