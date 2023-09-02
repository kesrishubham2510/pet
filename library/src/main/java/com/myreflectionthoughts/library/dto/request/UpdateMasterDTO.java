package com.myreflectionthoughts.library.dto.request;

import com.myreflectionthoughts.library.contract.IValidator;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import lombok.Data;

@Data
public class UpdateMasterDTO implements IValidator {
    private String id;
    private String name;
    private String email;
    private String password;
    private double age;
    private String address;

    @Override
    public void validate() {
        ValidationUtils.validateString(id, "Master ID");
        ValidationUtils.validateString(name,"name");
        ValidationUtils.validateEmail(email);
        ValidationUtils.validatePassword(password);
        ValidationUtils.validateAge(age);
        ValidationUtils.validateString(address,"address");

        this.setId(id.trim());
        this.setEmail(email.trim());
        this.setName(name.trim());
        this.setPassword(password.trim());
        this.setAddress(address.trim());
    }
}
