package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import jakarta.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingUtility {

    private final ConversionUtility conversionUtility;
    private final ModelMapper modelMapper;
    private final ServiceConstants serviceConstants;
    private final Logger logger;

    public MappingUtility(ConversionUtility conversionUtility, ModelMapper modelMapper) {
        this.conversionUtility = conversionUtility;
        this.modelMapper = modelMapper;
        serviceConstants = new ServiceConstants();
        logger = Logger.getLogger(MappingUtility.class.getName());
//        addMappingConfigurations();
    }

    @PostConstruct
    void initMappings(){
        addMappingConfigurations();
    }

    public Pet mapToPet(AddPetDTO addPetDTO) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating AddPetDTO --> Pet mapping......", Level.FINE);
        Pet pet = modelMapper.map(addPetDTO, Pet.class);
        pet.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);
        LogUtility.loggerUtility.logExit(logger, "Successfully mapped AddPetDTO --> Pet...", Level.FINE);
        return pet;
    }

    public Pet mapToPet(UpdatePetDTO updatePetDTO) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating UpdatePetDTO --> Pet mapping......", Level.FINE);
        Pet pet =  modelMapper.map(updatePetDTO, Pet.class);
        LogUtility.loggerUtility.logExit(logger, "Successfully mapped UpdatePetDTO --> Pet...", Level.FINE);
        return pet;
    }

    public PetDTO mapToPetDTO(Pet pet) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating Pet --> PetDTO mapping......", Level.FINE);
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        petDTO.setClinicCardStatusMessage(pet.getClinicCardStatus().getMessage());
        LogUtility.loggerUtility.logExit(logger, "Successfully mapped Pet --> PetDTO....", Level.FINE);
        return petDTO;
    }

    public DeletePetDTO createDeletePetDTO(String petId) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating DeletePetDTO generation for pet:- "+petId, Level.FINE);
        DeletePetDTO deletePetDTO = new DeletePetDTO();
        deletePetDTO.setId(petId);
        deletePetDTO.setMessage(String.format(serviceConstants.getPET_INFO_DELETED(), petId));
        LogUtility.loggerUtility.logExit(logger, "Successfully generated DeletePetDTO for pet:- "+petId, Level.FINE);
        return deletePetDTO;
    }

    public PetDTO setClinicCardStatus(PetDTO petDTO) {
        LogUtility.loggerUtility.logEntry(logger, "Setting up ClinicCardStatusMessage for pet:- "+petDTO.getId(), Level.FINE);
        petDTO.setClinicCardStatusMessage(
                ClinicCardStatus.valueOf(petDTO.getClinicCardStatus()).getMessage()
        );
        LogUtility.loggerUtility.logExit(logger, "ClinicCardStatusMessage set-up done...", Level.FINE);
        return petDTO;
    }

    private void addMappingConfigurations() {

        LogUtility.loggerUtility.logEntry(logger, "Initiating mapping configurations......", Level.FINE);

        TypeMap<AddPetDTO, Pet> addPetDTO_To_Pet_Mapper = modelMapper.createTypeMap(AddPetDTO.class, Pet.class);
        TypeMap<Pet, PetDTO> pet_To_PetTDTO_Mapper = modelMapper.createTypeMap(Pet.class, PetDTO.class);
        TypeMap<UpdatePetDTO, Pet> updatePetDTO_To_Pet_Mapper = modelMapper.createTypeMap(UpdatePetDTO.class, Pet.class);

        LogUtility.loggerUtility.logEntry(logger, "Initiating String --> Category mapping config for AddPetDTO --> Pet ...", Level.FINE);
        addPetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_categoryConverter).map(
                        AddPetDTO::getCategory, Pet::setCategory
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating String --> Gender mapping config for AddPetDTO --> Pet ...", Level.FINE);
        addPetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_genderConverter).map(
                        AddPetDTO::getGender, Pet::setGender
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating Gender --> String mapping config for Pet --> PetDTO ...", Level.FINE);
        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.gender_To_stringConverter).map(
                        Pet::getGender, PetDTO::setGender
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating Category --> String mapping config for Pet --> PetDTO ...", Level.FINE);
        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.category_To_stringConverter).map(
                        Pet::getCategory, PetDTO::setCategory
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating ClinicCardStatus --> String mapping config for Pet --> PetDTO...", Level.FINE);
        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.clinicCardStatus_To_stringConverter).map(
                        Pet::getClinicCardStatus, PetDTO::setClinicCardStatus
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating String --> ClinicCard mapping config for UpdatePetDTO --> Pet ...", Level.FINE);
        updatePetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_clinicCardStatusConverter).map(
                        UpdatePetDTO::getClinicCardStatus, Pet::setClinicCardStatus
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating String --> Category mapping config for UpdatePetDTO --> Pet ...", Level.FINE);
        updatePetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_categoryConverter).map(
                      UpdatePetDTO::getCategory, Pet::setCategory
                )
        );

        LogUtility.loggerUtility.logEntry(logger, "Initiating String --> Gender mapping config for UpdatePetDTO --> Pet ...", Level.FINE);
        updatePetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_genderConverter).map(
                      UpdatePetDTO::getGender, Pet::setGender
                )
        );

        LogUtility.loggerUtility.logExit(logger, "Successfully added mapping configurations...", Level.FINE);
    }
}
