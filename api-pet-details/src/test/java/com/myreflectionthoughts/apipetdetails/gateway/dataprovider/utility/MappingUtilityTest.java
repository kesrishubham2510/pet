/**
 * This is a Behaviour Driven Test written to check the
   modelMapper functionality
*/

package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MappingUtilityTest {

    private final String petId;
    private final String masterId;
    private final TestDataGenerator testDataGenerator;
    private final ServiceConstants serviceConstants;

    @Autowired
    private MappingUtility mapper;

    public MappingUtilityTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
        masterId = ServiceConstants.DUMMY_MASTER_ID;
        testDataGenerator = new TestDataGenerator();
        serviceConstants = new ServiceConstants();
    }

    @Test
    public void testMapToPet() {

        Pet expectedPet = TestDataGenerator.getPet();

        Pet actualPet = mapper.mapToPet(TestDataGenerator.getAddPetDTO());

        assertNotNull(actualPet);
        assertNotNull(actualPet.getCategory());
        assertNotNull(actualPet.getGender());
        assertNotNull(actualPet.getClinicCardStatus());
        assertEquals(expectedPet.getMasterId(), actualPet.getMasterId());
        assertEquals(expectedPet.getName(), actualPet.getName());
        assertEquals(expectedPet.getMaster(), actualPet.getMaster());
        assertEquals(expectedPet.getCategory(), actualPet.getCategory());
        assertEquals(expectedPet.getAge(), actualPet.getAge());
        assertEquals(expectedPet.getGender(), actualPet.getGender());
        assertEquals(expectedPet.getClinicCardStatus(), actualPet.getClinicCardStatus());
    }

    @Test
    public void testMapToPet_Throws_CategoryNotFoundException() {

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();
        addPetDTO.setCategory(ServiceConstants.VALID_CATEGORY_STRING+"fg");
        MappingException thrownException = assertThrows(MappingException.class, ()->mapper.mapToPet(addPetDTO));
        assertEquals(thrownException.getCause().getClass(), CategoryNotFoundException.class);
    }

    @Test
    public void testMapToPet_Throws_GenderNotFoundException() {

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();
        addPetDTO.setGender(ServiceConstants.VALID_GENDER_CATEGORY+"fg");
        MappingException thrownException = assertThrows(MappingException.class, ()->mapper.mapToPet(addPetDTO));
        assertEquals(thrownException.getCause().getClass(), GenderNotFoundException.class);
    }

    @Test
    public void testMapToPetDTO() {

        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        PetDTO actualPetDTO = mapper.mapToPetDTO(TestDataGenerator.getPet());

        assertNotNull(actualPetDTO);
        assertNotNull(actualPetDTO.getCategory());
        assertNotNull(actualPetDTO.getGender());
        assertNotNull(actualPetDTO.getClinicCardStatus());
        assertEquals(petId, actualPetDTO.getId());
        assertEquals(masterId, actualPetDTO.getMasterId());
        assertEquals(expectedPetDTO.getName(), actualPetDTO.getName());
        assertEquals(expectedPetDTO.getMaster(), actualPetDTO.getMaster());
        assertEquals(expectedPetDTO.getCategory(), actualPetDTO.getCategory());
        assertEquals(expectedPetDTO.getAge(), actualPetDTO.getAge());
        assertEquals(expectedPetDTO.getGender(), actualPetDTO.getGender());
        assertEquals(expectedPetDTO.getClinicCardStatus(), actualPetDTO.getClinicCardStatus());
    }

    @Test
    public void testMapToDeletePetDTO() {

        DeletePetDTO expectedDeletePetDTO = testDataGenerator.getDeletePetDTO();
        DeletePetDTO actualDeletePetDTO = mapper.createDeletePetDTO(petId);

        assertNotNull(actualDeletePetDTO);
        assertEquals(expectedDeletePetDTO,actualDeletePetDTO);
    }

    @Test
    public void testMapToPet_from_UpdatePetDTO(){

        Pet expectedUpdatedPet = TestDataGenerator.getUpdatedPet();
        Pet actualUpdatePet = mapper.mapToPet(TestDataGenerator.getUpdatePetDTO());

        assertEquals(expectedUpdatedPet, actualUpdatePet);
    }

    @Test
    public void testMapToPet_from_UpdatePetDTO_Throws_ClinicCardStatusException(){

        UpdatePetDTO updatePetDTO = TestDataGenerator.getUpdatePetDTO();
        updatePetDTO.setClinicCardStatus(ServiceConstants.VALID_CLINIC_CARD_STATUS+"fvf");

        MappingException thrownException = assertThrows(MappingException.class, ()->mapper.mapToPet(updatePetDTO));
        assertEquals(thrownException.getCause().getClass(), ClinicCardStatusNotFoundException.class);
    }
}
