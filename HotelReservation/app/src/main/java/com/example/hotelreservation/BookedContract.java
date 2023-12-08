package com.example.hotelreservation;

public class BookedContract {
    private BookedContract() {
        // Private constructor to prevent instantiation
    }

    public static class BookedEntry {
        public static final String TABLE_NAME = "Booked";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_HOTEL_NAME = "hotelName";
        // Add other columns as necessary
    }
}
