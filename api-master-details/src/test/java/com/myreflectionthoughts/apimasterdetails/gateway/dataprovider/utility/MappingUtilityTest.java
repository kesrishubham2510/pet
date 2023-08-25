package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MappingUtilityTest {

    private final MappingUtility mappingUtility;

    public MappingUtilityTest() {
        mappingUtility = new MappingUtility();
    }

    @Test
    void testMapToMaster() {
        AddMasterDTO addMasterDTO = TestDataGenerator.generateAddMasterDTO();
        Master expectedMaster = TestDataGenerator.generateMaster();

        // since the addMasterDTO does not contain the id
        expectedMaster.setId(null);

        Master actualMaster = mappingUtility.mapToMaster(addMasterDTO);
        assertEquals(expectedMaster, actualMaster);
    }

    @Test
    void testMapToMasterDTO() {
        Master master = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();
        MasterDTO actualMasterDTO = mappingUtility.mapToMasterDTO(master);
        assertEquals(expectedMasterDTO, actualMasterDTO);
    }

    @Test
    void testMapToMasterDTO_from_UpdateMasterDTO() {
        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        Master expectedMaster = TestDataGenerator.generateUpdatedMaster();
        Master actualMaster = mappingUtility.mapToMaster(updateMasterDTO);
        assertEquals(expectedMaster, actualMaster);
    }
}
