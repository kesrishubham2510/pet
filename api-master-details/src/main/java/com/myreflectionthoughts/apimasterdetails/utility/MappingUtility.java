package com.myreflectionthoughts.apimasterdetails.utility;

import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MappingUtility {

    @Autowired
    private ModelMapper modelMapper;

    public Master mapToMaster(AddMasterDTO addMasterDTO) {
        return modelMapper.map(addMasterDTO, Master.class);
    }

    public MasterDTO mapToMasterDTO(Master master) {
        return modelMapper.map(master, MasterDTO.class);
    }

}
