package com.example.tikito.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.SeatItem;

import java.util.List;

public class SeatAdaptor extends RecyclerView.Adapter<SeatAdaptor.MyViewHolder>
{
    Context context;
    List<SeatItem> seatItemList;

    private OnSeatSelectedListener listener;

    public SeatAdaptor(Context context, List<SeatItem> seatItemList, OnSeatSelectedListener listener) {
        this.context = context;
        this.listener = listener;
        this.seatItemList = seatItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adaptor_seat, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SeatItem s = seatItemList.get(position);

        if(s.isSelected())
        {
            holder.txtSeatNo.setBackgroundResource(R.drawable.bg_selected);
        }
        else
        {
            holder.txtSeatNo.setBackgroundResource(R.drawable.bg_available);
        }


        holder.txtSeatNo.setText(s.getSeatNo());

        holder.itemView.setOnClickListener(v ->
        {
            s.setSelected(!s.isSelected()); //toggle selection
            List<SeatItem> selectedSeats = new ArrayList<>();

            for (SeatItem seat : seatItemList) {
                if (seat.isSelected()) {
                    selectedSeats.add(seat);
                }
            }

            listener.onSeatSelectionChanged(selectedSeats);

            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return seatItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtSeatNo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSeatNo = itemView.findViewById(R.id.txtSeatNo);
        }
    }

    public interface OnSeatSelectedListener
    {
        void onSeatSelectionChanged(List<SeatItem> selectedSeats);
    }
}
