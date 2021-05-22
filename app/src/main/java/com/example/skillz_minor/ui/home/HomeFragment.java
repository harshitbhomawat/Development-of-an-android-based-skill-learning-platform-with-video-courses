package com.example.skillz_minor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillz_minor.R;
import com.example.skillz_minor.courseModelClass;
import com.example.skillz_minor.firebaseAdapter;
import com.example.skillz_minor.newsAdapter;
import com.example.skillz_minor.newsmodelclass;
import com.example.skillz_minor.topcourseAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    RecyclerView toprecyclerView,newrecyclerView;
    RecyclerView newsrecycler;
    topcourseAdapter adapter;
    newsAdapter newsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);
        toprecyclerView = view.findViewById(R.id.topcourserecyclerview);
        newrecyclerView = view.findViewById(R.id.newcourserecyclerView);
        newsrecycler = view.findViewById(R.id.newsrecycler);
        toprecyclerView.setHasFixedSize(true);
        newrecyclerView.setHasFixedSize(true);
        newsrecycler.setHasFixedSize(true);
        toprecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        newrecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        newsrecycler.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        FirebaseRecyclerOptions<courseModelClass> options= new FirebaseRecyclerOptions.Builder<courseModelClass>().setQuery(FirebaseDatabase.getInstance().getReference().child("courses"),courseModelClass.class).build();
        FirebaseRecyclerOptions<newsmodelclass> options1= new FirebaseRecyclerOptions.Builder<newsmodelclass>().setQuery(FirebaseDatabase.getInstance().getReference().child("news"),newsmodelclass.class).build();
        adapter = new topcourseAdapter(options);
        newsAdapter = new newsAdapter(options1);
        //initData();
        toprecyclerView.setAdapter(adapter);
        newrecyclerView.setAdapter(adapter);
        newsrecycler.setAdapter(newsAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        newsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        newsAdapter.stopListening();
    }
}