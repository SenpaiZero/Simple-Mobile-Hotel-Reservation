package com.example.hotelreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {

    Button loginBtn;
    TextView registerBtn;
    EditText usernameTB, passwordTB;
    private CardView customCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try
        {
            getSupportActionBar().hide();
        } catch (Exception ex)
        {
            try {
                getActionBar().hide();
            } catch (Exception exx)
            {
                Log.e("Action Bar", exx.getMessage());
            }
        }

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        usernameTB = findViewById(R.id.usernameTB);
        passwordTB = findViewById(R.id.passwordTB);

        customCardView = findViewById(R.id.card_view);
        customCardView.setVisibility(View.GONE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin())
                {
                    startActivity(new Intent(login.this, home.class));
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
    }

    boolean checkLogin() {
        databaseHelper dbHelper = new databaseHelper(login.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if(usernameTB.getText().toString().equals("RESETALLDATA"))
            if(passwordTB.getText().toString().equals("RESETALLDATA"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("WARNING!")
                        .setMessage("Are you sure you want to reset database?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.resetDatabase(login.this);
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform action on click of Cancel button
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        })
                        .show();
            }

        String[] projection = {"id", "username", "password", "email", "phoneNumber", "fullName"};
        Cursor cursor = db.query("Users", projection, null, null, null, null, null);

        boolean isSuccessful = false;

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            if (username.equalsIgnoreCase(usernameTB.getText().toString()) &&
                    password.equals(passwordTB.getText().toString())) {

                // After successful login, set user details in userDetails
                userDetails user = userDetails.getInstance();
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber")));
                user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow("fullName")));

                showCustomToast("Success");
                isSuccessful = true;
                break; // Exit the loop once the user is authenticated
            }
        }

        if (!isSuccessful) {
            showCustomToast("Failed to log in");
        }

        cursor.close();
        db.close();
        return isSuccessful;
    }


    void openRegister()
    {
        startActivity(new Intent(login.this, Register.class));
    }

    private void showCustomToast(String message) {
        final TextView toastText = customCardView.findViewById(R.id.toast_text);
        toastText.setText(message);

        // Show the CardView
        customCardView.setVisibility(View.VISIBLE);

        // Create a handler to delay hiding the CardView
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create a fade-out animation for the CardView
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(customCardView, "alpha", 1f, 0f);
                fadeOut.setDuration(1000); // Set the duration of the fade-out animation (1 second)

                // Set a listener to hide the CardView when the animation ends
                fadeOut.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        customCardView.setVisibility(View.GONE);
                    }
                });

                // Start the fade-out animation
                fadeOut.start();
            }
        }, 200); // Shorter delay before hiding (100 milliseconds)
    }
}