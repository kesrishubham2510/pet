package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.apipetdetails.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.GenderNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MappingUtilityTest {

    @Autowired
    private MappingUtility mapper;

    @Autowired
    private ExceptionUtility exceptionUtility;

    @Test
    public void testMapToPet() {

        Pet pet = mapper.mapToPet(TestDataGenerator.getAddPetDTO());

        assertNotNull(pet);
        assertNotNull(pet.getCategory());
        assertNotNull(pet.getGender());
        assertNotNull(pet.getClinicCardStatus());
        assertEquals("pet-name", pet.getName());
        assertEquals("master", pet.getMaster());
        assertEquals(Category.DOG, pet.getCategory());
        assertEquals(1.0, pet.getAge());
        assertEquals(Gender.MALE, pet.getGender());
        assertEquals(ClinicCardStatus.NOT_APPLIED, pet.getClinicCardStatus());
    }

    @Test
    public void testMapToPetDTO() {

        PetDTO petDTO = mapper.mapToPetDTO(TestDataGenerator.getPet());

        assertNotNull(petDTO);
        assertNotNull(petDTO.getCategory());
        assertNotNull(petDTO.getGender());
        assertNotNull(petDTO.getClinicCardStatus());
        assertEquals("id", petDTO.getId());
        assertEquals("pet-name", petDTO.getName());
        assertEquals("master", petDTO.getMaster());
        assertEquals("DOG", petDTO.getCategory());
        assertEquals(1.0, petDTO.getAge());
        assertEquals("FEMALE", petDTO.getGender());
        assertEquals("NOT_APPLIED", petDTO.getClinicCardStatus());
        assertEquals(ClinicCardStatus.NOT_APPLIED.getMessage(), petDTO.getClinicCardStatusMessage());
    }

    @Test
    public void testMapToPet_ThrowsCategoryNotFoundException() {

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();
        addPetDTO.setCategory("mouse");

        var thrownException = assertThrows(MappingException.class, () -> mapper.mapToPet(addPetDTO));

        assertEquals(CategoryNotFoundException.class, thrownException.getCause().getClass());
        assertEquals(exceptionUtility.getCategoryNotFoundExceptionMessage("mouse"), thrownException.getCause().getMessage());
    }

    @Test
    public void testMapToPet_ThrowsGenderNotFoundException() {

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();
        addPetDTO.setGender("new-gender");

        var thrownException = assertThrows(MappingException.class, () -> mapper.mapToPet(addPetDTO));

        assertEquals(GenderNotFoundException.class, thrownException.getCause().getClass());
        assertEquals(exceptionUtility.getGenderNotFoundExceptionMessage(), thrownException.getCause().getMessage());
    }


}
