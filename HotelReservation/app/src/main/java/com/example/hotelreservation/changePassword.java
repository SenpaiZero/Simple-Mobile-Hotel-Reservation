package com.example.hotelreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class changePassword extends AppCompatActivity {

    Button backBtn, updateBtn;
    EditText currentPass, newPass, reNewPass;
    private CardView customCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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

        backBtn = findViewById(R.id.backBtn);
        updateBtn = findViewById(R.id.changePassBtn);

        currentPass = findViewById(R.id.currentPassTB);
        newPass = findViewById(R.id.passwordTB);
        reNewPass = findViewById(R.id.rePasswordTB);

        customCardView = findViewById(R.id.card_view);
        customCardView.setVisibility(View.GONE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(changePassword.this, home.class));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    void changePassword()
    {
        if(currentPass.getText().toString().isEmpty())
        {
            showCustomToast("Current Password is empty.");
            return;
        }

        if(newPass.getText().toString().isEmpty())
        {
            showCustomToast("New Password is empty.");
            return;
        }

        if(reNewPass.getText().toString().isEmpty())
        {
            showCustomToast("Re-Enter Password is empty.");
            return;
        }

        databaseHelper db = new databaseHelper(this);
        userDetails user = userDetails.getInstance();
        if(db.getPasswordForUsername(user.getUsername()).equals(currentPass.getText().toString()))
        {
            if(newPass.getText().toString().equals(reNewPass.getText().toString()))
            {
                if(db.updatePassword(user.getUsername(), newPass.getText().toString()))
                {
                    showCustomToast("You've successfully updated your password.");
                }
            }
            else
            {
                showCustomToast("The password must be the same.");
            }
        }
        else
        {
            showCustomToast("Incorrect Current Password");
        }
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