package com.example.skillz_minor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    EditText title,desc,link,image;
    TextView totalLinks;
    Button add,submit;
    String  stitle,sdesc,slink,simage,stotal;
    ArrayList<String> videolist;
    FirebaseDatabase database;
    DatabaseReference reference;
    enrollHelperClass enrollHelperClass;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        title = findViewById(R.id.addtitle);
        desc = findViewById(R.id.addDescription);
        link = findViewById(R.id.addLink);
        image = findViewById(R.id.addImage);
        totalLinks = findViewById(R.id.showNumber);
        add = (Button)findViewById(R.id.addbtn);
        submit = (Button)findViewById(R.id.submitbtn);
        videolist = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("courses");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slink = link.getText().toString().trim();
                videolist.add(slink);
                count += 1;
                totalLinks.setText(String.valueOf(count)+". "+slink);
                Toast.makeText(AdminActivity.this,"added" + slink,Toast.LENGTH_LONG).show();
                link.setText("");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stitle = title.getText().toString().trim();
                sdesc = desc.getText().toString().trim();
                simage = image.getText().toString().trim();
                if(stitle.isEmpty() || sdesc.isEmpty() || simage.isEmpty() || videolist.isEmpty()){
                    Toast.makeText(AdminActivity.this, "Some Field is Empty. Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                enrollHelperClass = new enrollHelperClass(sdesc,simage,stitle,videolist);
                reference.child(enrollHelperClass.getTitle()).setValue(enrollHelperClass);
                Toast.makeText(AdminActivity.this,"Course added successfully",Toast.LENGTH_SHORT).show();
                count=0;
                title.setText("");
                image.setText("");
                desc.setText("");
                videolist.clear();
            }}
        });
    }
}