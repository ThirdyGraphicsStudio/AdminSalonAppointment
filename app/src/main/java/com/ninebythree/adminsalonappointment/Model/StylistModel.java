package com.ninebythree.adminsalonappointment.Model;

public class StylistModel {
    private int imageResource;
    private String name;
    private String specialty;
    private float averageRating;
    private int reviews;

    public StylistModel(int imageResource, String name, String specialty, float averageRating, int reviews) {
        this.imageResource = imageResource;
        this.name = name;
        this.specialty = specialty;
        this.averageRating = averageRating;
        this.reviews = reviews;
    }

    public int getImageResource() {
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
