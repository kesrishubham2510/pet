package com.myreflectionthoughts.apimasterdetails.utility;

import com.myreflectionthoughts.apimasterdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MappingUtilityTest {

    @Autowired
    private MappingUtility mappingUtility;

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

}
