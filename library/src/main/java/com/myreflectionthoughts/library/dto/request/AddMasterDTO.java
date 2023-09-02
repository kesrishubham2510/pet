package com.myreflectionthoughts.library.dto.request;

import com.myreflectionthoughts.library.contract.IValidator;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMasterDTO implements IValidator {
    private String name;
    private String email;
    private String password;
    private double age;
    private String address;


    @Override
    public void validate() {
        ValidationUtils.validateString(name,"name");
        ValidationUtils.validateEmail(email);
        ValidationUtils.validatePassword(password);
        ValidationUtils.validateAge(age);
        ValidationUtils.validateString(address,"address");

        this.setEmail(email.trim());
        this.setName(name.trim());
        this.setPassword(password.trim());
        this.setAddress(address.trim());
    }
}
