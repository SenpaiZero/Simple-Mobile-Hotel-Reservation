package com.example.hotelreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Register extends AppCompatActivity {

    Button registerBtn;
    TextView cancelBtn;
    private CardView customCardView;
    EditText phoneTB, emailTB, passTB, rePassTB, usernameTB, nameTB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        registerBtn = findViewById(R.id.registerBtn2);
        cancelBtn = findViewById(R.id.cancelBtn);
        phoneTB = findViewById(R.id.phoneTB);
        emailTB = findViewById(R.id.emailTB);
        passTB = findViewById(R.id.passwordTB);
        rePassTB = findViewById(R.id.rePasswordTB);
        usernameTB = findViewById(R.id.usernameTB);
        nameTB = findViewById(R.id.nameTB);

        customCardView = findViewById(R.id.card_view);
        customCardView.setVisibility(View.GONE);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmptyTB()) {
                    databaseHelper dbHelper = new databaseHelper(Register.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("username", usernameTB.getText().toString());
                    values.put("password", passTB.getText().toString());
                    values.put("email", emailTB.getText().toString());
                    values.put("phoneNumber", phoneTB.getText().toString());
                    values.put("fullName", nameTB.getText().toString());

                    try {
                        long newRowId = db.insert("Users", null, values);
                        if (newRowId != -1) {
                            showCustomToast("Success");
                            startActivity(new Intent(Register.this, login.class));
                        } else {
                            showCustomToast("Username already exist.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showCustomToast("Error: " + e.getMessage());
                    } finally {
                        db.close();
                    }
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, login.class));
            }
        });
    }
    boolean checkEmptyTB() {
        if (nameTB.getText().toString().isEmpty()) {
            showCustomToast("Full Name is invalid");
            return false;
        }
        String email = emailTB.getText().toString();
        if (email.isEmpty() || !email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            showCustomToast("Email Address is invalid");
            return false;
        }
        if (phoneTB.getText().toString().isEmpty()) {
            showCustomToast("Phone Number is invalid.");
            return false;
        }
        String username = usernameTB.getText().toString();
        if (username.isEmpty() || !username.matches("^\\S+$")) {
            showCustomToast("Username is invalid");
            return false;
        }
        if (passTB.getText().toString().isEmpty()) {
            showCustomToast("Password is empty");
            return false;
        }
        if (!passTB.getText().toString().equals(rePassTB.getText().toString())) {
            showCustomToast("Passwords are not the same");
            return false;
        }
        return true;
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