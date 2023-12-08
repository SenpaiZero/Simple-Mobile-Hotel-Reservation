package Fragments; // Update package name if needed

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservation.Hotel;
import com.example.hotelreservation.HotelAdapter;
import com.example.hotelreservation.R;
import com.example.hotelreservation.databaseHelper;
import com.example.hotelreservation.userDetails;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment implements HotelAdapter.OnBookButtonClickListener{
    private RecyclerView recyclerView;
    private HotelAdapter adapter;
    private List<Hotel> hotelList;
    private CardView popup;
    String hotelNameStr;
    DatePicker date;
    TimePicker time;
    CardView customCardView;
    private Button confirmBtn, cancelBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_book, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        popup = view.findViewById(R.id.dateTimePopup);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        date = view.findViewById(R.id.datePicker);
        time = view.findViewById(R.id.timePicker);

        customCardView = view.findViewById(R.id.card_view);
        customCardView.setVisibility(View.GONE);

        popup.setVisibility(View.GONE);
        hotelList = new ArrayList<>();
        hotelList.add(new Hotel(R.drawable.zen_hotel, 2480,
                "ZenHotel", "4.2", "Santiago City", "Zen Hotel Cafe and Restaurant"));
        hotelList.add(new Hotel(R.drawable.espacio_hotel, 3120,
                "Espacio Hotel", "4.6", "Taguig, 1630 Metro Manila", "Parking    " +
                "Wi-Fi    " +
                "Air conditioning    " +
                "Kitchen in rooms"));
        hotelList.add(new Hotel(R.drawable.danicia_carreon, 2423,
                "Chocolate N Berries Hotel", "3.5", "Baliwag, Bulacan", "4 Star Hotel"));
        hotelList.add(new Hotel(R.drawable.on_hotel, 3010,
                "ON Hotel", "3.9", "Zone, 2023 Pampanga", "In a mainly residential area, this relaxed hotel is 2 km from the Mimosa Plus Golf Course, 5 km from Korea Town Angeles."));
        hotelList.add(new Hotel(R.drawable.on_hotel, 2400,
                "Yuan's Place", "4.7", "Malolos", "Balcony      Kitchen      Pet-friendly"));
        hotelList.add(new Hotel(R.drawable.yyy_townhouse, 3800,
                "YYY's Townhouse", "4.7", "Malolos", "This property offers access to a balcony, free private parking and free WiFi."));

        adapter = new HotelAdapter(getActivity(), hotelList);
        adapter.setOnBookButtonClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.GONE);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper dbHelper = new databaseHelper(getActivity());
                userDetails user = userDetails.getInstance();

                int hour = time.getHour();
                int minute = time.getMinute();

                String time = "";
                if(hour < 10) time+= "0";
                time += hour + ":";

                if(minute < 10) time+= "0";
                time += minute;

                int day = date.getDayOfMonth();
                int month = date.getMonth() + 1;
                int year = date.getYear();
                dbHelper.insertBooked(getActivity(),
                        user.getUsername(),
                        hotelNameStr,
                        month + "-" + day + "-" + year,
                        time);
                showCustomToast("Successfully booked: " + hotelNameStr);
                popup.setVisibility(View.GONE);
            }
        });
        return view;
    }
    @Override
    public void onBookButtonClick(int position) {
        Hotel clickedHotel = hotelList.get(position);
        hotelNameStr = clickedHotel.getHotelName();
        popup.setVisibility(View.VISIBLE);


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
