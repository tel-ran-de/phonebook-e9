package com.telran.phonebookapi.entity;

public enum EmailType {
    WORK ("Work"),
    HOME ("Home"),
    OTHER ("Other");

    private final String type;

    EmailType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
