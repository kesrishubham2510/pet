package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddUserDTO {
    private AddMasterDTO master;
    private List<AddPetDTO> pets;
}
