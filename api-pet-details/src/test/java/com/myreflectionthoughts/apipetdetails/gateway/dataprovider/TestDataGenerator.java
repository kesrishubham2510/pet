package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

public class TestDataGenerator {

    private final static String petId;
    private final static String masterId;

    static {
        petId = ServiceConstants.DUMMY_PET_ID;
        masterId = ServiceConstants.DUMMY_MASTER_ID;
    }

    private final ServiceConstants serviceConstants;

    public TestDataGenerator() {
        this.serviceConstants = new ServiceConstants();
    }

    public static AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setMasterId(masterId);
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("female");
        addPetDTO.setName("pet-name");
        addPetDTO.setMaster("master");
        return addPetDTO;
    }

    public static Pet getPet() {
        Pet pet = new Pet();
        pet.setMasterId(masterId);
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
        pet.setMasterId(masterId);
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

    public static UpdatePetDTO getUpdatePetDTO(){
        UpdatePetDTO updatePetDTO = new UpdatePetDTO();
        updatePetDTO.setMasterId(masterId);
        updatePetDTO.setId(petId);
        updatePetDTO.setAge(1.0);
        updatePetDTO.setCategory("DOG");
        updatePetDTO.setGender("FEMALE");
        updatePetDTO.setName("pet-name-updated");
        updatePetDTO.setMaster("master-updated");
        updatePetDTO.setClinicCardStatus("UNDER_PRogrESS");
        return updatePetDTO;
    }

    public static Pet getUpdatedPet(){
        Pet updatedPet = new Pet();
        updatedPet.setMasterId(masterId);
        updatedPet.setId(petId);
        updatedPet.setAge(1.0);
        updatedPet.setCategory(Category.DOG);
        updatedPet.setGender(Gender.FEMALE);
        updatedPet.setName("pet-name-updated");
        updatedPet.setMaster("master-updated");
        updatedPet.setClinicCardStatus(ClinicCardStatus.UNDER_PROGRESS);
        return updatedPet;
    }

    public static PetDTO getUpdatedPetDTO(){
        PetDTO updatedPetDTO = new PetDTO();
        updatedPetDTO.setMasterId(masterId);
        updatedPetDTO.setId(petId);
        updatedPetDTO.setAge(1.0);
        updatedPetDTO.setCategory("DOG");
        updatedPetDTO.setGender("FEMALE");
        updatedPetDTO.setName("pet-name-updated");
        updatedPetDTO.setMaster("master-updated");
        updatedPetDTO.setClinicCardStatus("UNDER_PROGRESS");
        updatedPetDTO.setClinicCardStatusMessage(ClinicCardStatus.valueOf(ServiceConstants.VALID_CLINIC_CARD_STATUS).getMessage());
        return updatedPetDTO;
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

    public String getClinicCardStatusNotFoundExceptionStringTemplate() {
        return serviceConstants.getCLINIC_CARD_STATUS_NOT_FOUND_EXCEPTION();
    }

    public static List<Pet> generateDummyPets(){

        Pet pet1 = generatePetWithDummyMongoDB_ID();

        Pet pet2 = new Pet();
        pet2.setId((new ObjectId()).toString());
        pet2.setMasterId(masterId);
        pet2.setAge(2.0);
        pet2.setCategory(Category.CAT);
        pet2.setGender(Gender.MALE);
        pet2.setName("pet2");
        pet2.setMaster("master2");
        pet2.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);

        Pet pet3 = new Pet();
        pet3.setId((new ObjectId()).toString());
        pet3.setMasterId(masterId);
        pet3.setAge(3.0);
        pet3.setCategory(Category.RABBIT);
        pet3.setGender(Gender.MALE);
        pet3.setName("pet3");
        pet3.setMaster("master3");
        pet3.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);

        Pet pet4 = new Pet();
        pet4.setId((new ObjectId()).toString());
        pet4.setMasterId(masterId);
        pet4.setAge(3.0);
        pet4.setCategory(Category.HAMSTER);
        pet4.setGender(Gender.FEMALE);
        pet4.setName("pet4");
        pet4.setMaster("master4");
        pet4.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);

        return Arrays.asList(pet1,pet2,pet3,pet4);
    }

    public static Pet generatePetWithDummyMongoDB_ID(){
        Pet pet = new Pet();
        pet.setId((new ObjectId(ServiceConstants.DUMMY_MONGO_DB_ID)).toString());
        pet.setMasterId(masterId);
        pet.setAge(1.0);
        pet.setCategory(Category.DOG);
        pet.setGender(Gender.FEMALE);
        pet.setName("pet");
        pet.setMaster("master1");
        pet.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);
        return pet;
    }
}
