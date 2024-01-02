package com.ninebythree.adminsalonappointment.Model;

public class ScheduleModel {
    String id;
    String image;
    String name;
    String specialty;
    String date;
    String time;
    String status;
    String clientName;
    String clientAddress;

    String paymentMethod;
    String gcashImage;

    public ScheduleModel(String id,String image, String name, String specialty, String date, String time, String status, String clientName, String clientAddress) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.specialty = specialty;
        this.date = date;
        this.time = time;
        this.status = status;
        this.clientName = clientName;
        this.clientAddress = clientAddress;

    }

    public ScheduleModel(String id, String image, String name, String specialty, String date, String time, String status, String clientName, String clientAddress, String paymentMethod, String gcashImage) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.specialty = specialty;
        this.date = date;
        this.time = time;
        this.status = status;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.paymentMethod = paymentMethod;
        this.gcashImage = gcashImage;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getGcashImage() {
        return gcashImage;
    }

    public String getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }


    public String getImage() {
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
