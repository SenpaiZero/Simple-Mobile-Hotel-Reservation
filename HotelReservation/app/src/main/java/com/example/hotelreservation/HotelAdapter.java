package com.example.hotelreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservation.Hotel;
import com.example.hotelreservation.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.imageView.setImageResource(hotel.getImage());
        holder.priceTextView.setText("$" + hotel.getPrice());
        holder.hotelName.setText(hotel.getHotelName());
        holder.details.setText(hotel.getDetails());
        holder.rating.setText(hotel.getRating() + "/5");
        holder.location.setText(hotel.getLocation());

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onBookButtonClick(position);
                }
            }
        });
    }
    public interface OnBookButtonClickListener {
        void onBookButtonClick(int position);
    }

    private OnBookButtonClickListener listener;

    public void setOnBookButtonClickListener(OnBookButtonClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceTextView;
        TextView hotelName;
        TextView rating;
        TextView location;
        TextView details;
        Button bookButton;

        public HotelViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotelImage);
            priceTextView = itemView.findViewById(R.id.priceTxt);
            hotelName = itemView.findViewById(R.id.hotelName);
            rating = itemView.findViewById(R.id.starRatingText);
            location = itemView.findViewById(R.id.locationText);
            details = itemView.findViewById(R.id.detailsTxt);
            bookButton = itemView.findViewById(R.id.checkIn);
        }
    }
}
