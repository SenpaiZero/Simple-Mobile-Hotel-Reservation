package com.example.hotelreservation;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class databaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 4;

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "email TEXT," +
                "phoneNumber TEXT," +
                "fullName TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Booked (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "hotelName TEXT," +
                "date TEXT," +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if needed
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Booked");
        onCreate(db);
    }
    public void resetDatabase(Context context) {
        databaseHelper dbHelper = new databaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM Users"); // Delete all rows in the Users table
        db.execSQL("DELETE FROM Booked"); // Delete all rows in the booked table

        db.close();
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rowsAffected = db.update("Users", values, "username = ?", new String[]{username});
        db.close();

        return rowsAffected > 0;
    }
    @SuppressLint("Range")
    public String getPasswordForUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;

        Cursor cursor = db.query("Users", new String[]{"password"}, "username = ?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
        }

        db.close();
        return password;
    }

    public boolean insertBooked(Context context, String username, String hotelName, String date, String time)
    {
        databaseHelper dbHelper = new databaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("hotelName", hotelName);
        values.put("date", date);
        values.put("time", time);

        try {
            long newRowId = db.insert("Booked", null, values);
            if (newRowId != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public List<booked> getBookingsByUsername(String username) {
        List<booked> bookedList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BookedContract.BookedEntry.COLUMN_NAME_DATE,
                BookedContract.BookedEntry.COLUMN_NAME_TIME,
                BookedContract.BookedEntry.COLUMN_NAME_HOTEL_NAME
        };

        String selection = BookedContract.BookedEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                BookedContract.BookedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndexOrThrow(BookedContract.BookedEntry.COLUMN_NAME_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(BookedContract.BookedEntry.COLUMN_NAME_TIME));
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(BookedContract.BookedEntry.COLUMN_NAME_HOTEL_NAME));

                // Create a Booked object and add it to the list
                bookedList.add(new booked(getImage(hotelName), hotelName, date, time));
            }
            cursor.close();
        }
        db.close();
        return bookedList;
    }

    public int getImage(String hotelName) {
        if (hotelName.equalsIgnoreCase("ZenHotel")) {
            return R.drawable.zen_hotel;
        } else if (hotelName.equalsIgnoreCase("Espacio Hotel")) {
            return R.drawable.espacio_hotel;
        } else if (hotelName.equalsIgnoreCase("Chocolate N Berries Hotel")) {
            return R.drawable.danicia_carreon;
        } else if (hotelName.equalsIgnoreCase("ON Hotel")){
            return R.drawable.on_hotel;
        }else if (hotelName.equalsIgnoreCase("Yuan's Place")) {
            return R.drawable.yuan_place;
        }else if (hotelName.equalsIgnoreCase("YYY's Townhouse")) {
            return R.drawable.yyy_townhouse;
        }

        return R.drawable.hotel_logo;
    }

    public boolean deleteBooking(String username, String hotelName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "username = ? AND hotelName = ? AND date = ? AND time = ?";
        String[] whereArgs = {
                username, hotelName, date, time
        };

        int deletedRows = db.delete("Booked", whereClause, whereArgs);

        db.close();

        return deletedRows > 0;
    }
    public boolean updateBookingDateTime(String username, String hotelName, String newDate, String newTime, String oldDate, String oldTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", newDate);
        values.put("time", newTime);

        String whereClause = "username = ? AND hotelName = ? AND time = ? AND date = ?";
        String[] whereArgs = {username, hotelName, oldTime, oldDate};

        int updatedRows = db.update("Booked", values, whereClause, whereArgs);

        db.close();

        return updatedRows > 0;
    }
}
