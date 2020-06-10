package com.telran.phonebookapi.entity;

public enum ContactType {
    WORK ("Work"),
    HOME ("Home"),
    FAMILY ("Family"),
    FRIENDS ("Friends"),
    OTHER ("Other");

    private final String type;

    ContactType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
