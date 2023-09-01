package com.myreflectionthoughts.library.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserDTO {
    private UpdateMasterDTO latestUserInfo;
    private List<UpdatePetDTO> latestPetsInfo;
}
