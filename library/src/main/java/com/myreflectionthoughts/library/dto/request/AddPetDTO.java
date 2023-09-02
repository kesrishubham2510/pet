package com.myreflectionthoughts.library.dto.request;

import com.myreflectionthoughts.library.contract.IValidator;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import lombok.Data;

@Data
public class AddPetDTO implements IValidator {
    private String name;
    // to map a pet With master
    private String master;
    private String category;
    private String gender;
    private double age;

    @Override
    public void validate() {

        ValidationUtils.validateString(name, "Pet name");
        ValidationUtils.validateString(master, "Master ID");
        ValidationUtils.validateString(category,"Category");
        ValidationUtils.validateString(gender, "Gender");
        ValidationUtils.validatePetAge(age);

        this.setName(name.trim());
        this.setMaster(master.trim());
        this.setCategory(category.trim());
        this.setGender(gender.trim());
    }
}
