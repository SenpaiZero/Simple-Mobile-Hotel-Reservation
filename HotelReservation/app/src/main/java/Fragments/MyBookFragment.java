package Fragments; // Update package name if needed

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservation.Hotel;
import com.example.hotelreservation.HotelAdapter;
import com.example.hotelreservation.HotelBookedAdapter;
import com.example.hotelreservation.R;
import com.example.hotelreservation.booked;
import com.example.hotelreservation.databaseHelper;
import com.example.hotelreservation.login;
import com.example.hotelreservation.userDetails;

import java.util.List;

public class MyBookFragment extends Fragment
        implements HotelBookedAdapter.OnUpdateButtonClickListener,
        HotelBookedAdapter.OnCancelButtonClickListener{
    private RecyclerView recyclerView;
    private HotelBookedAdapter adapter;
    private List<booked> bookList;
    CardView popup;
    Button cancelBtn, updateBtn;
    Button popupUpdate, popupCancel;
    DatePicker date;
    TimePicker time;
    int pos;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_books, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        updateBtn = view.findViewById(R.id.editBtn);
        date = view.findViewById(R.id.datePicker);
        time = view.findViewById(R.id.timePicker);

        popup = view.findViewById(R.id.dateTimePopup);
        popup.setVisibility(View.GONE);

        popupCancel = view.findViewById(R.id.cancelBtn2);
        popupUpdate = view.findViewById(R.id.confirmBtn);

        databaseHelper dbHelper = new databaseHelper(getActivity());
        userDetails user = userDetails.getInstance();
        String username = user.getUsername();
        bookList = dbHelper.getBookingsByUsername(username);
        adapter = new HotelBookedAdapter(getActivity(), bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnUpdateButtonClickListener(this);
        adapter.setOnCancelButtonClickListener(this);
        recyclerView.setAdapter(adapter);

        popupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.GONE);
            }
        });

        popupUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booked updatedBooking = bookList.get(pos);
                userDetails user = userDetails.getInstance();
                int hour = time.getHour();
                int minute = time.getMinute();

                String newTime = "";
                if(hour < 10) newTime+= "0";
                newTime += hour + ":";

                if(minute < 10) newTime+= "0";
                newTime += minute;

                int day = date.getDayOfMonth();
                int month = date.getMonth() + 1;
                int year = date.getYear();
                String newDate = month + "-" + day + "-" + year;

                databaseHelper dbHelper = new databaseHelper(getActivity());
                boolean isUpdated = dbHelper.updateBookingDateTime(
                        user.getUsername(),
                        updatedBooking.getHotelName(),
                        newDate,
                        newTime,
                        updatedBooking.getDate(),
                        updatedBooking.getTime()
                );

                if (isUpdated) {
                    adapter.notifyItemChanged(pos);
                    popup.setVisibility(View.GONE);
                    loadData();
                } else {
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when the Fragment resumes
        loadData();
    }
    private void loadData() {
        databaseHelper dbHelper = new databaseHelper(getActivity());
        userDetails user = userDetails.getInstance();
        String username = user.getUsername();
        bookList = dbHelper.getBookingsByUsername(username);

        if (adapter == null) {
            adapter = new HotelBookedAdapter(getActivity(), bookList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter.setOnUpdateButtonClickListener(this);
            adapter.setOnCancelButtonClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {
            // Update the existing adapter's dataset
            adapter.updateData(bookList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUpdateButtonClick(int position) {
        pos = position;
        popup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCancelButtonClick(int position) {

        booked cancelledBooking = bookList.get(position);
        databaseHelper dbHelper = new databaseHelper(getActivity());
        userDetails user = userDetails.getInstance();
        String username = user.getUsername();
        String hotelName = cancelledBooking.getHotelName();
        String date = cancelledBooking.getDate();
        String time = cancelledBooking.getTime();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("WARNING!")
                .setMessage("Are you sure you want to cancel your appointment?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.deleteBooking(username, hotelName, date, time))
                        {
                            loadData();
                        }
                        dialog.dismiss();
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
}
