package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String bloodType;
    private String location;
    private String contactInfo;

    // Constructors
    public Donor() {}

    public Donor(String name, String bloodType, String location, String contactInfo) {
        this.name = name;
        this.bloodType = bloodType;
        this.location = location;
        this.contactInfo = contactInfo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}
