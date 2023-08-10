package com.myreflectionthoughts.apipetdetails.constant;

import lombok.Getter;

@Getter
public class ServiceConstants {

    private String CATEGORY_NOT_FOUND_EXCEPTION = "We currently do not provide service for %s in our clinic, please select from the available category options [ %s ]";
    private String GENDER_NOT_FOUND_EXCEPTION = "Please enter a correct gender, please select from the available gender options [ %s ]";
    private String CLINIC_CARD_STATUS_NOT_FOUND_EXCEPTION = "Please enter a correct clinic card status, please select from the available status options [ %s ]";
    private String PET_INFO_DELETED = "The information for pet with id:- %s has been deleted successfully";

    public static final String PET_NOT_FOUND_EXCEPTION = "Pet not found";
    public static final String DUMMY_PET_ID = "id-1";
    public static final String VALID_CATEGORY_STRING = "DOG";
    public static final String VALID_GENDER_CATEGORY = "MALE";
    public static final String VALID_CLINIC_CARD_STATUS = "NOT_APPLIED";
    public static final String API_QUALIFIER = "api-pet-details";
}
