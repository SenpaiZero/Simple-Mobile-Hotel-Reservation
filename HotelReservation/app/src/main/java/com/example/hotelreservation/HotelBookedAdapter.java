package com.example.hotelreservation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class HotelBookedAdapter extends RecyclerView.Adapter<HotelBookedAdapter.BookedViewHolder> {

    private List<booked> bookedList;
    private Context context;

    public HotelBookedAdapter(Context context, List<booked> bookedList) {
        this.context = context;
        this.bookedList = bookedList;
    }
    public void updateData(List<booked> updatedList) {
        this.bookedList.clear();
        this.bookedList.addAll(updatedList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_hotel_booked, parent, false);
        return new BookedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedViewHolder holder, @SuppressLint("RecyclerView") int position) {
        booked book = bookedList.get(position);
        holder.imageView.setImageResource(book.getImage());
        holder.hotelName.setText(book.getHotelName());
        holder.time.setText(book.getTime());
        holder.date.setText(book.getDate());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateListener != null) {
                   updateListener.onUpdateButtonClick(position);
                }
            }
        });

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelListener != null) {
                    cancelListener.onCancelButtonClick(position);
                }
            }
        });
    }
    public interface OnUpdateButtonClickListener {
        void onUpdateButtonClick(int position);
    }
    public interface OnCancelButtonClickListener {
        void onCancelButtonClick(int position);
    }
    private OnUpdateButtonClickListener updateListener;
    private OnCancelButtonClickListener cancelListener;

    public void setOnUpdateButtonClickListener(OnUpdateButtonClickListener listener) {
        this.updateListener = listener;
    }
    public void setOnCancelButtonClickListener(OnCancelButtonClickListener listener) {
        this.cancelListener = listener;
    }
    @Override
    public int getItemCount() {
        return bookedList.size();
    }

    public static class BookedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date, time, hotelName;
        Button cancelBtn, updateBtn;

        public BookedViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotelImage);
            hotelName = itemView.findViewById(R.id.hotelName);
            date = itemView.findViewById(R.id.dateData);
            time = itemView.findViewById(R.id.timeData);

            cancelBtn = itemView.findViewById(R.id.cancelBtn);
            updateBtn = itemView.findViewById(R.id.editBtn);
        }
    }
}
