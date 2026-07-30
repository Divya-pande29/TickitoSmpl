package com.example.tikito.fragments;

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
import com.example.tikito.adapters.CategoryAdapter;
import com.example.tikito.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment  extends Fragment {

    RecyclerView categoryRecyclerView;

    List<Category> categoryList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);


        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);


        categoryList = new ArrayList<>();


        categoryList.add(new Category("Movie", R.drawable.ic_movie));
        categoryList.add(new Category("Concert", R.drawable.ic_concert));
        categoryList.add(new Category("Event", R.drawable.ic_event));
        categoryList.add(new Category("Stage Show", R.drawable.ic_stage));


        CategoryAdapter adapter = new CategoryAdapter(categoryList);


        categoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );


        categoryRecyclerView.setAdapter(adapter);


        return view;
    }
}


