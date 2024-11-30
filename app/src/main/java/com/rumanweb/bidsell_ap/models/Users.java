package com.rumanweb.bidsell_ap.models;

public class Users {
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String additionalInfo;

    public Users(String fullName, String userName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Users(String fullName, String userName, String email, String phoneNumber, String additionalInfo) {
        this(fullName, userName, email, phoneNumber);
        this.additionalInfo = additionalInfo;
    }

    // Getters
    public String getFullName() { return fullName; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAdditionalInfo() { return additionalInfo; }
}