package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MappingUtilityTest {

    private final String petId;
    private final TestDataGenerator testDataGenerator;
    @InjectMocks
    private MappingUtility mapper;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ServiceConstants serviceConstants;

    public MappingUtilityTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
        testDataGenerator = new TestDataGenerator();
    }

    @Test
    public void testMapToPet() {

        Pet expectedPet = TestDataGenerator.getPet();
        when(modelMapper.map(any(), any())).thenReturn(expectedPet);

        Pet actualPet = mapper.mapToPet(TestDataGenerator.getAddPetDTO());

        assertNotNull(actualPet);
        assertNotNull(actualPet.getCategory());
        assertNotNull(actualPet.getGender());
        assertNotNull(actualPet.getClinicCardStatus());
        assertEquals(expectedPet.getName(), actualPet.getName());
        assertEquals(expectedPet.getMaster(), actualPet.getMaster());
        assertEquals(expectedPet.getCategory(), actualPet.getCategory());
        assertEquals(expectedPet.getAge(), actualPet.getAge());
        assertEquals(expectedPet.getGender(), actualPet.getGender());
        assertEquals(expectedPet.getClinicCardStatus(), actualPet.getClinicCardStatus());
    }

    @Test
    public void testMapToPetDTO() {

        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(modelMapper.map(any(), any())).thenReturn(expectedPetDTO);

        PetDTO actualPetDTO = mapper.mapToPetDTO(TestDataGenerator.getPet());

        assertNotNull(actualPetDTO);
        assertNotNull(actualPetDTO.getCategory());
        assertNotNull(actualPetDTO.getGender());
        assertNotNull(actualPetDTO.getClinicCardStatus());
        assertEquals(petId, actualPetDTO.getId());
        assertEquals(expectedPetDTO.getName(), actualPetDTO.getName());
        assertEquals(expectedPetDTO.getMaster(), actualPetDTO.getMaster());
        assertEquals(expectedPetDTO.getCategory(), actualPetDTO.getCategory());
        assertEquals(expectedPetDTO.getAge(), actualPetDTO.getAge());
        assertEquals(expectedPetDTO.getGender(), actualPetDTO.getGender());
        assertEquals(expectedPetDTO.getClinicCardStatus(), actualPetDTO.getClinicCardStatus());
        assertEquals(expectedPetDTO.getClinicCardStatusMessage(), actualPetDTO.getClinicCardStatusMessage());
    }

    @Test
    public void testMapToDeletePetDTO() {

        when(serviceConstants.getPET_INFO_DELETED()).thenReturn(testDataGenerator.getPetInfoDeletedStringTemplate());
        DeletePetDTO deletePetDTO = mapper.createDeletePetDTO(petId);

        assertNotNull(deletePetDTO);
        assertEquals(testDataGenerator.getDeletePetDTO(), deletePetDTO);
    }
}
