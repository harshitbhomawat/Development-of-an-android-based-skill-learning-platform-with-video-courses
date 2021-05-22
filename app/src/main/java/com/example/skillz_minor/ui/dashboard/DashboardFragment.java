package com.example.skillz_minor.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillz_minor.Courses;
import com.example.skillz_minor.R;
import com.example.skillz_minor.courseModelClass;
import com.example.skillz_minor.firebaseAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    firebaseAdapter adapter;
    FirebaseAuth firebaseAuth;
    ArrayList<Courses> itemList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_dashboard,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //initData();
        FirebaseRecyclerOptions<courseModelClass> options= new FirebaseRecyclerOptions.Builder<courseModelClass>().setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("enrolled"),courseModelClass.class).build();
        adapter = new firebaseAdapter(options);
        //initData();
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

   /* private ArrayList<Courses> initData() {
        itemList = new ArrayList<>();
        itemList.add(new Courses(R.drawable.embroidery_pic,"Embroidery Training",1));
        itemList.add(new Courses(R.drawable.jewellery_making,"Jewellery Making",2));
        itemList.add(new Courses(R.drawable.organic_farming,"Organic Farming",3));
        itemList.add(new Courses(R.drawable.electrical_training,"Electrical Training",4));
        itemList.add(new Courses(R.drawable.customer_care,"Corporate Ethics",5));
        itemList.add(new Courses(R.drawable.game_dev,"Game Designing Basics",6));
        itemList.add(new Courses(R.drawable.musical_pic,"Music Course",7));
        itemList.add(new Courses(R.drawable.skills_logo,"Soft Skills",8));

        return itemList;
    }*/
}