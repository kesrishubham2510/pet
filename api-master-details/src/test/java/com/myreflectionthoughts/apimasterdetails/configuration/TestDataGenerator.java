package com.myreflectionthoughts.apimasterdetails.configuration;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
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

    public static UpdateMasterDTO generateUpdateMasterDTO(){
        UpdateMasterDTO updateMasterDTO = new UpdateMasterDTO();
        updateMasterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        updateMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updateMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL+"-updated");
        updateMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updateMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        updateMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updateMasterDTO;
    }

    public static Master generateUpdatedMaster(){
        Master updatedMaster = new Master();
        updatedMaster.setId(ServiceConstants.VALID_MASTER_ID);
        updatedMaster.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updatedMaster.setEmail(ServiceConstants.VALID_MASTER_EMAIL+"-updated");
        updatedMaster.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updatedMaster.setAge(ServiceConstants.VALID_MASTER_AGE);
        updatedMaster.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updatedMaster;
    }

    public static MasterDTO generateUpdatedMasterDTO(){
        MasterDTO updatedMasterDTO = new MasterDTO();
        updatedMasterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        updatedMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updatedMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL+"-updated");
        updatedMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updatedMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        updatedMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updatedMasterDTO;
    }
}
