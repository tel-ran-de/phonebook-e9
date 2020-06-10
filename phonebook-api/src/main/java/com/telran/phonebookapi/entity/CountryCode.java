package com.telran.phonebookapi.entity;

public enum CountryCode {
    USA ("1", "Usa"),
    RUSSIA ("7", "Russia"),
    GERMANY ("49", "Germany"),
    UKRAINE ("380", "Ukraine"),
    BULGARIA ("359", "Bulgaria");

    private final String code;
    private final String country;

    CountryCode(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }
}
