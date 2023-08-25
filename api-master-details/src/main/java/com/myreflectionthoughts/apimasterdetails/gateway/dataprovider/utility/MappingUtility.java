package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.modelmapper.ModelMapper;

public class MappingUtility {
    private final ModelMapper modelMapper;

    public MappingUtility() {
        this.modelMapper = new ModelMapper();
    }


    public Master mapToMaster(AddMasterDTO addMasterDTO) {
        return modelMapper.map(addMasterDTO, Master.class);
    }

    public MasterDTO mapToMasterDTO(Master master) {
        return modelMapper.map(master, MasterDTO.class);
    }

    public Master mapToMaster(UpdateMasterDTO updateMasterDTO) {
        return modelMapper.map(updateMasterDTO, Master.class);
    }
}
