package com.myreflectionthoughts.apipetdetails.data;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;

public class TestDataGenerator {

    private final static String petId;

    static {
        petId = ServiceConstants.DUMMY_PET_ID;
    }

    private final ServiceConstants serviceConstants;

    public TestDataGenerator() {
        this.serviceConstants = new ServiceConstants();
    }

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
        pet.setId(petId);
        pet.setAge(1.0);
        pet.setCategory(Category.DOG);
        pet.setGender(Gender.FEMALE);
        pet.setName("pet-name");
        pet.setMaster("master");
        pet.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);
        return pet;
    }

    public static PetDTO getPetDTO() {
        PetDTO pet = new PetDTO();
        pet.setId(petId);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setMaster("master");
        pet.setClinicCardStatus("NOT_APPLIED");
        return pet;
    }

    public DeletePetDTO getDeletePetDTO() {
        DeletePetDTO deletePetDTO = new DeletePetDTO();
        deletePetDTO.setId(petId);
        deletePetDTO.setMessage(String.format(serviceConstants.getPET_INFO_DELETED(), petId));
        return deletePetDTO;
    }

    public String getPetId() {
        return petId;
    }

    public String getPetInfoDeletedStringTemplate() {
        return serviceConstants.getPET_INFO_DELETED();
    }

    public String getGetCategoryNotFoundExceptionStringTemplate() {
        return serviceConstants.getCATEGORY_NOT_FOUND_EXCEPTION();
    }

    public String getGetGenderNotFoundExceptionStringTemplate() {
        return serviceConstants.getGENDER_NOT_FOUND_EXCEPTION();
    }
}
