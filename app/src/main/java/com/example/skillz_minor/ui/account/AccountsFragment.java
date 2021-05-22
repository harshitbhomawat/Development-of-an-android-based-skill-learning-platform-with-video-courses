package com.example.skillz_minor.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.skillz_minor.LoginActivity;
import com.example.skillz_minor.MainActivity;
import com.example.skillz_minor.R;
import com.example.skillz_minor.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class AccountsFragment extends Fragment {
    TextView name,totalEnrolled,totalCompleted,profile,changepassword,shareapp,rateapp;
    ImageView profilepic;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    public Button signout;
    FirebaseAuth firebaseAuth;
    String sname,stotalEnrolled,stotalCompleted;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_account,container,false);
        signout= view.findViewById(R.id.signout);
        name = (TextView) view.findViewById(R.id.Name);
        shareapp = (TextView) view.findViewById(R.id.shareapptext);
        rateapp = (TextView) view.findViewById(R.id.rateapptext);
        profilepic = (ImageView) view.findViewById(R.id.profilepicture);
        profilepic.setImageResource(R.drawable.ic_baseline_person_24);
        totalEnrolled = (TextView) view.findViewById(R.id.numberOfEnrolled);
        totalCompleted = (TextView) view.findViewById(R.id.numberOfCompleted);
        firebaseAuth = FirebaseAuth.getInstance();
        Bundle bundle = this.getArguments();
        sname = bundle.getString("sname");
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");
        Query query = reference.orderByChild("personal details/uid").equalTo(firebaseAuth.getUid());
        query.addListenerForSingleValueEvent(valueEventListener);
        name.setText(sname);
        totalEnrolled.setText(stotalEnrolled);
        totalCompleted.setText(stotalCompleted);
        rateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri1 = Uri.parse("https://play.google.com");
                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id="+MainActivity.getContext.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"unable to open",Toast.LENGTH_SHORT).show();
                }
            }
        });
        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody="Skillz(Learn self employment skills) \n https://play.google.com/store/apps/details?id="+MainActivity.getContext.getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                try {
                    startActivity(Intent.createChooser(intent,"Share Using"));
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"unable to share"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.getContext, WelcomeActivity.class));
                }
            }
        });
        return view;
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                stotalEnrolled = String.valueOf(snapshot.child(firebaseAuth.getUid()).child("enrolled").getChildrenCount());
                stotalCompleted = String.valueOf(snapshot.child(firebaseAuth.getUid()).child("courses").getChildrenCount());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
};}