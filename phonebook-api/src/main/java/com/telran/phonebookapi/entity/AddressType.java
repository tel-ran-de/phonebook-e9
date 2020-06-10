package com.telran.phonebookapi.entity;

public enum AddressType {
    WORK ("Work"),
    HOME ("Home"),
    OTHER ("Other");

    private final String type;

    AddressType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
