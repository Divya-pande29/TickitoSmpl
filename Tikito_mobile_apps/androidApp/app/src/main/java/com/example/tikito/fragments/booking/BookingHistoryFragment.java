package com.example.tikito.fragments.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.BookingHistoryAdapter;
import com.example.tikito.entities.BookingHistory;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryFragment extends Fragment {

    private RecyclerView recyclerBookingHistory;

    private BookingHistoryAdapter adapter;

    private List<BookingHistory> historyList;

    public BookingHistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);

        initViews(view);

        loadDummyData();

        return view;
    }

    private void initViews(View view) {

        recyclerBookingHistory = view.findViewById(R.id.recyclerBookingHistory);

        recyclerBookingHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        historyList = new ArrayList<>();
    }

    private void loadDummyData() {

        historyList.add(new BookingHistory(
                "Interstellar",
                "PVR Pune",
                "10 Jul 2026",
                "7:00 PM",
                "B5",
                "Completed"));

        historyList.add(new BookingHistory(
                "Oppenheimer",
                "INOX Pune",
                "05 Jul 2026",
                "9:30 PM",
                "C9",
                "Completed"));

        historyList.add(new BookingHistory(
                "Kalki 2898 AD",
                "Cinepolis",
                "28 Jun 2026",
                "5:00 PM",
                "D12",
                "Completed"));

        adapter = new BookingHistoryAdapter(historyList);

        recyclerBookingHistory.setAdapter(adapter);
    }
}