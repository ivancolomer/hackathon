package com.ibar.protectme;

public class Coordinates {
    public double longitude;
    public double latitude;

    public Coordinates(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return "{"+String.valueOf(latitude)+", "+String.valueOf(longitude)+"}";
    }
}
