package com.ninebythree.adminsalonappointment.Model;

public class UsersModel {
    // Assuming these are the fields in your Firestore documents
    private String id;
    private String name;
    private String email; // or double, depending on what you use
    private String address;

    private String contactNumber;

    // Constructor, getters, and setters...
    public UsersModel() {
        // Empty constructor required for Firestore
    }

    public UsersModel(String id, String name, String email, String address, String contactNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
