package com.telran.phonebookapi.entity;

public enum PhoneType {
    WORK ("Work"),
    HOME ("Home"),
    OTHER ("Other");

    private final String type;

    PhoneType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
