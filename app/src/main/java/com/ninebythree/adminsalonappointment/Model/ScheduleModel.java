package com.ninebythree.adminsalonappointment.Model;

public class ScheduleModel {
    int image;
    String name;
    String specialty;
    String date;
    String time;
    String status;


    public ScheduleModel(int image, String name, String specialty, String date, String time, String status) {
        this.image = image;
        this.name = name;
        this.specialty = specialty;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
