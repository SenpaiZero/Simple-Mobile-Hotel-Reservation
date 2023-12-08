package Fragments; // Update package name if needed

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.hotelreservation.R;
import com.example.hotelreservation.changePassword;
import com.example.hotelreservation.login;
import com.example.hotelreservation.userDetails;

public class MyProfileFragment extends Fragment{
    CardView personalDetails, changePass, logout, personalDetailsCard;
    Button back;
    TextView userNameTxt, userNameTxt2, fullNameTxt, emailTxt, phoneNumberTxt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_profile, container, false);

        personalDetailsCard = view.findViewById(R.id.detailCard);
        personalDetailsCard.setVisibility(View.GONE);

        userNameTxt = view.findViewById(R.id.nameTxtProfile);

        back = view.findViewById(R.id.closeBtn);
        fullNameTxt = view.findViewById(R.id.fullnameData);
        userNameTxt2 = view.findViewById(R.id.usernameData);
        phoneNumberTxt = view.findViewById(R.id.phoneNumberData);
        emailTxt = view.findViewById(R.id.emailData);

        userDetails user = userDetails.getInstance();
        userNameTxt.setText(user.getUsername());

        fullNameTxt.setText(user.getFullName());
        userNameTxt2.setText(user.getUsername());
        emailTxt.setText(user.getEmail());
        phoneNumberTxt.setText(user.getPhoneNumber());

        personalDetails = view.findViewById(R.id.personalDetails);
        changePass = view.findViewById(R.id.changePassword);
        logout = view.findViewById(R.id.logout);

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetailsCard.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetailsCard.setVisibility(View.GONE);
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), changePassword.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), login.class));
            }
        });

        return view;
    }
}
