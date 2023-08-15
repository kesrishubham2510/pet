package com.myreflectionthoughts.apimasterdetails.data;

import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;

public class TestDataGenerator {

    public static AddMasterDTO generateAddMasterDTO(){
        AddMasterDTO addMasterDTO = new AddMasterDTO();
        addMasterDTO.setName("master-1");
        addMasterDTO.setEmail("email@gmail.com");
        addMasterDTO.setAddress("address-1");
        addMasterDTO.setAge(23.0);
        addMasterDTO.setPassword("password-1");
        return addMasterDTO;
    }

    public static MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setName("master-1");
        masterDTO.setEmail("email@gmail.com");
        masterDTO.setAddress("address-1");
        masterDTO.setAge(23.0);
        masterDTO.setPassword("password-1");
        return masterDTO;
    }

    public static Master generateMaster(){
        Master master = new Master();
        master.setName("master-1");
        master.setEmail("email@gmail.com");
        master.setAddress("address-1");
        master.setAge(23.0);
        master.setPassword("password-1");
        return master;
    }


}
