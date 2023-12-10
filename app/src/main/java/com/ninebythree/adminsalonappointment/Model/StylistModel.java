package com.ninebythree.adminsalonappointment.Model;

public class StylistModel {
    private String id;
    private String imageResource;
    private String name;
    private String specialty;
    private float averageRating;
    private int reviews;
    private String experience;
    private String description;


    public StylistModel(String id, String imageResource, String name, String specialty, float averageRating, int reviews, String experience, String description) {
        this.id = id;
        this.imageResource = imageResource;
        this.name = name;
        this.specialty = specialty;
        this.averageRating = averageRating;
        this.reviews = reviews;
        this.experience = experience;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getExperience() {
        return experience;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getReviews() {
        return reviews;
    }
}
