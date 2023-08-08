package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MappingUtility {

    @Autowired
    private ConversionUtility conversionUtility;
    @Autowired
    private ModelMapper modelMapper;
    private ServiceConstants serviceConstants;

    @PostConstruct
    public void configureModelMapper() {
        addMappingConfigurations();
        serviceConstants = new ServiceConstants();
    }


    public Pet mapToPet(AddPetDTO addPetDTO) {
        Pet pet = modelMapper.map(addPetDTO, Pet.class);
        pet.setClinicCardStatus(ClinicCardStatus.NOT_APPLIED);
        return pet;
    }

    public PetDTO mapToPetDTO(Pet pet) {
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        petDTO.setClinicCardStatusMessage(pet.getClinicCardStatus().getMessage());
        return petDTO;
    }

    public DeletePetDTO createDeletePetDTO(String petId) {
        DeletePetDTO deletePetDTO = new DeletePetDTO();
        deletePetDTO.setId(petId);
        deletePetDTO.setMessage(String.format(serviceConstants.getPET_INFO_DELETED(), petId));
        return deletePetDTO;
    }

    private void addMappingConfigurations() {

        TypeMap<AddPetDTO, Pet> addPetDTO_To_Pet_Mapper = modelMapper.createTypeMap(AddPetDTO.class, Pet.class);
        TypeMap<Pet, PetDTO> pet_To_PetTDTO_Mapper = modelMapper.createTypeMap(Pet.class, PetDTO.class);

        addPetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_categoryConverter).map(
                        AddPetDTO::getCategory, Pet::setCategory
                )
        );

        addPetDTO_To_Pet_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.string_To_genderConverter).map(
                        AddPetDTO::getGender, Pet::setGender
                )
        );

        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.gender_To_stringConverter).map(
                        Pet::getGender, PetDTO::setGender
                )
        );

        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.category_To_stringConverter).map(
                        Pet::getCategory, PetDTO::setCategory
                )
        );

        pet_To_PetTDTO_Mapper.addMappings(
                mapper -> mapper.using(conversionUtility.clinicCardStatus_To_stringConverter).map(
                        Pet::getClinicCardStatus, PetDTO::setClinicCardStatus
                )
        );
    }
}
