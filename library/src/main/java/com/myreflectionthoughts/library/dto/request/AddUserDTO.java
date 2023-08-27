package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddUserDTO {
    private AddMasterDTO addMasterDTO;
    private List<AddPetDTO> addPetDTO;
}
