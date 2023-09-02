package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.bson.types.ObjectId;

import java.util.List;

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
        updateMasterDTO.setEmail("updated-"+ServiceConstants.VALID_MASTER_EMAIL);
        updateMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updateMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        updateMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updateMasterDTO;
    }

    public static Master generateUpdatedMaster(){
        Master updatedMaster = new Master();
        updatedMaster.setId(ServiceConstants.VALID_MASTER_ID);
        updatedMaster.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updatedMaster.setEmail("updated-"+ServiceConstants.VALID_MASTER_EMAIL);
        updatedMaster.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updatedMaster.setAge(ServiceConstants.VALID_MASTER_AGE);
        updatedMaster.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updatedMaster;
    }

    public static MasterDTO generateUpdatedMasterDTO(){
        MasterDTO updatedMasterDTO = new MasterDTO();
        updatedMasterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        updatedMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updatedMasterDTO.setEmail("updated-"+ServiceConstants.VALID_MASTER_EMAIL);
        updatedMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updatedMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        return updatedMasterDTO;
    }

    public static List<Master> generateDummyMasters(){

        Master master1 = TestDataGenerator.generateMasterWithMongoId();
        master1.setName("master-1");

        Master master2 = new Master();
        master2.setId("masterId-2");
        master2.setName("master2");
        master2.setEmail("master2@gmail.com");
        master2.setAddress("address@master2");
        master2.setAge(20);
        master2.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);

        Master master3 = new Master();
        master3.setId("masterId-3");
        master3.setName("master3");
        master3.setEmail("master3@gmail.com");
        master3.setAddress("address@master3");
        master3.setAge(21);
        master3.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);

        Master master4 = new Master();
        master4.setId("masterId-4");
        master4.setName("master4");
        master4.setEmail("master4@gmail.com");
        master4.setAddress("address@master4");
        master4.setAge(22);
        master4.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);

        return List.of(master1, master2, master3, master4);
    }

    public static Master generateMasterWithMongoId(){
        Master master = TestDataGenerator.generateMaster();
        master.setId(new ObjectId(ServiceConstants.DUMMY_MONGO_DB_ID).toString());
        return master;
    }
}
