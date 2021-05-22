package com.example.skillz_minor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skillz_minor.ui.dashboard.DashboardFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;


public class CourseFragment extends Fragment {
    private static Boolean enrolled=false;
    RecyclerView recyclerView;
    ArrayList<videos> itemList;
    ArrayList<String> links;
    String imglink;
    String passtitle,description;
    ImageView cimage;
    TextView ctitle,cdesc;
    Button enrollbtn;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference userref;
    FirebaseAuth firebaseAuth;
    enrollHelperClass enrollHelperClass;
    public CourseFragment(String passtitle,String link,String desc){
        this.passtitle=passtitle;
        this.imglink = link;
        this.description = desc;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_course,container,false);
        cimage = view.findViewById(R.id.contentimage);
        ctitle = view.findViewById(R.id.contenttitle);
        cdesc = view.findViewById(R.id.contentdescription);
        enrollbtn =(Button) view.findViewById(R.id.enrollbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        itemList = new ArrayList<>();
        links = new ArrayList<>();
        itemList.clear();
        links.clear();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("courses");
        userref = database.getReference("users");
        Query query = reference.orderByChild("title").equalTo(passtitle);
        Log.d(TAG, "onCreateView: query"+ query);
        Log.d(TAG, "onCreateView: out of links");
        ctitle.setText(passtitle);
        Glide.with(cimage.getContext()).load(imglink).into(cimage);
        recyclerView = view.findViewById(R.id.contentrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Query checkquery = userref.child(firebaseAuth.getUid()).child("enrolled");
        enrolled = false;
        checkquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(passtitle)){
                    enrolled = true;
                    enrollbtn.setText("Disenroll");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query.addListenerForSingleValueEvent(valueEventListener);
        enrollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enrolled){
                userref.child(firebaseAuth.getUid()).child("enrolled").child(enrollHelperClass.getTitle()).setValue(enrollHelperClass);
                enrollbtn.setText("Disenroll");
                enrolled = true;
            }
                else{
                    userref.child(firebaseAuth.getUid()).child("enrolled").child(enrollHelperClass.getTitle()).setValue(null);
                    enrollbtn.setText("Enroll");
                    AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,dashboardFragment).commit();
                    enrolled = false;
                }

            }
        });
        return view;
    }
    public static Boolean enablechecker(){
        return enrolled;
    }
   ValueEventListener valueEventListener = new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.exists()){
               Log.d(TAG, "onDataChange: **exists**");
               Log.d(TAG, "onDataChange: child link"+snapshot.child("title").getValue());
               for (DataSnapshot dss:snapshot.child(passtitle).child("list").getChildren()){
                   String link = dss.getValue(String.class);
                   Log.d(TAG, "onDataChange: links one by one "+link);
                   links.add(link);
               }
               for(int i=0;i<links.size();i++){
                   int num = i+1;
                   itemList.add(new videos(String.valueOf(num)+")","video"+String.valueOf(num),links.get(i)));
               }
               enrollHelperClass = new enrollHelperClass(description,imglink,passtitle,links);
               recyclerView.setAdapter(new contentAdapter(itemList));
           }
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   };
}