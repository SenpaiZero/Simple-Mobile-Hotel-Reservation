package com.example.hotelreservation;

public class booked {
    int imageResource;
    String hotelName, date, time;
    public booked(int imageResource, String hotelName, String date, String time) {
        this.imageResource = imageResource;
        this.hotelName = hotelName;
        this.date = date;
        this.time = time;
    }

    public int getImage() {return imageResource;}
    public String getHotelName() {return hotelName;}
    public String getDate() {return date;}
    public String getTime() {return time;}
}
