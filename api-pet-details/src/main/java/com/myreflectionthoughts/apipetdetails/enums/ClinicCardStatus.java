package com.myreflectionthoughts.apipetdetails.enums;

public enum ClinicCardStatus {
    COLLECTED("Card is made and successfully distributed"),
    UNDER_PROGRESS("The information has been collected, card making is under progress"),
    NOT_APPLIED("User has not applied for the Card yet");

    private final String message;

    ClinicCardStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}