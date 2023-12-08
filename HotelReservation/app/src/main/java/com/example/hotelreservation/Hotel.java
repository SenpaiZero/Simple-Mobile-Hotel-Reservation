package com.example.hotelreservation;

public class Hotel {
    private int imageResource; // Resource ID for the hotel image
    private int price; // Price of the hotel
    private String hotelName, rating, location, details;

    public Hotel(int imageResource, int price, String hotelName, String rating, String location, String details) {
        this.imageResource = imageResource;
        this.price = price;
        this.hotelName = hotelName;
        this.rating = rating;
        this.location = location;
        this.details = details;
    }

    public int getImage() {
        return imageResource;
    }

    public void setImage(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHotelName() {return hotelName;}
    public String getRating() {return rating;}
    public String getLocation() {return location;}
    public String getDetails() {return details;}
}
