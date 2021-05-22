package com.example.skillz_minor.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillz_minor.Courses;
import com.example.skillz_minor.R;
import com.example.skillz_minor.courseModelClass;
import com.example.skillz_minor.firebaseAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Courses> itemList;
    firebaseAdapter adapter;
    SearchView searchView;
    Button searchbtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_search,container,false);
        recyclerView = view.findViewById(R.id.srecycler);
        searchView = view.findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
       /* searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<courseModelClass> options= new FirebaseRecyclerOptions.Builder<courseModelClass>().setQuery(FirebaseDatabase.getInstance().getReference().child("courses"),courseModelClass.class).build();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.navigation_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void processsearch(String s){
        FirebaseRecyclerOptions<courseModelClass> options= new FirebaseRecyclerOptions.Builder<courseModelClass>().setQuery(FirebaseDatabase.getInstance().getReference().child("courses").orderByChild("title").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff"),courseModelClass.class).build();
        adapter = new firebaseAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}