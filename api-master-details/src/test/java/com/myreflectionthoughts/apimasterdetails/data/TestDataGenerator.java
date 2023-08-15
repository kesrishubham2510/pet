package com.myreflectionthoughts.apimasterdetails.data;

import com.myreflectionthoughts.apimasterdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;

public class TestDataGenerator {

    public static AddMasterDTO generateAddMasterDTO(){
        AddMasterDTO addMasterDTO = new AddMasterDTO();
        addMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME);
        addMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL);
        addMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS);
        addMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        addMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);
        return addMasterDTO;
    }

    public static MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        masterDTO.setName(ServiceConstants.VALID_MASTER_NAME);
        masterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL);
        masterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS);
        masterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        masterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);
        return masterDTO;
    }

    public static Master generateMaster(){
        Master master = new Master();
        master.setId(ServiceConstants.VALID_MASTER_ID);
        master.setName(ServiceConstants.VALID_MASTER_NAME);
        master.setEmail(ServiceConstants.VALID_MASTER_EMAIL);
        master.setAddress(ServiceConstants.VALID_MASTER_ADDRESS);
        master.setAge(ServiceConstants.VALID_MASTER_AGE);
        master.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);
        return master;
    }
}
