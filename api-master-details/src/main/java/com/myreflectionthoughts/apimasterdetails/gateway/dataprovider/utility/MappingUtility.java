package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.modelmapper.ModelMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingUtility {
    private final ModelMapper modelMapper;
    private final Logger logger;

    public MappingUtility() {
        this.modelMapper = new ModelMapper();
        this.logger = Logger.getLogger(MappingUtility.class.getName());
    }
    
    public Master mapToMaster(AddMasterDTO addMasterDTO) {
        LogUtility.loggerUtility.log(logger, "Initiating AddMasterDTO --> Master mapping...", Level.FINE);
        Master master = modelMapper.map(addMasterDTO, Master.class);
        LogUtility.loggerUtility.log(logger, "Mapping AddMasterDTO --> Master done successfully...", Level.FINE);
        return master;
    }

    public MasterDTO mapToMasterDTO(Master master) {
        LogUtility.loggerUtility.log(logger, "Initiating Master --> MasterDTO mapping...", Level.FINE);
        MasterDTO masterDTO = modelMapper.map(master, MasterDTO.class);
        LogUtility.loggerUtility.log(logger, "Mapping Master --> MasterDTO done successfully...", Level.FINE);
        return  masterDTO;
    }

    public Master mapToMaster(UpdateMasterDTO updateMasterDTO) {
        LogUtility.loggerUtility.log(logger, "Initiating UpdateMasterDTO --> Master mapping...", Level.FINE);
        Master master = modelMapper.map(updateMasterDTO, Master.class);
        LogUtility.loggerUtility.log(logger, "Mapping UpdateMasterDTO --> Master done successfully...", Level.FINE);
        return master;
    }
}
