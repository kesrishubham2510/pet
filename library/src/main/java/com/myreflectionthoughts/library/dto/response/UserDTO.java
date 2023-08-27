package com.myreflectionthoughts.library.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private MasterDTO masterDTO;
    private List<PetDTO> petDTO;
}
