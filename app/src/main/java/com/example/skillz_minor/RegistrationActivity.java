package com.example.skillz_minor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public FloatingActionButton regtowel;
    public Button regbtn;
    EditText fullname,password,cpassword,email,mob;
    Spinner spinner;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = findViewById(R.id.mytoolbar);
        //setSupportActionBar(toolbar);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        spinner = findViewById(R.id.reggender);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String gender = spinner.getSelectedItem().toString();
        fullname=findViewById(R.id.regname);
        password=findViewById(R.id.regpass);
        cpassword=findViewById(R.id.regconfirmpass);
        email=findViewById(R.id.regemail);
        mob=findViewById(R.id.regphone);
        regbtn = findViewById(R.id.regbtn);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spass = password.getText().toString();
                String cspass = cpassword.getText().toString();
                String smob = mob.getText().toString().trim();
                String sname = fullname.getText().toString().trim();
                if(TextUtils.isEmpty(sname)){
                    fullname.setError("Enter your name");
                    return;
                }
                if(TextUtils.isEmpty(smob)){
                    cpassword.setError("Enter mobile number");
                    return;
                }
                if(TextUtils.isEmpty(semail)){
                    email.setError("Email is required");
                    return;
                }
                if(!(semail.contains("@")) || !(semail.contains("."))){
                    email.setError("Please enter valid email");
                }
                if(TextUtils.isEmpty(spass)){
                    password.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(cspass)){
                    cpassword.setError("Please confirm password");
                    return;
                }
                if(spass.length() < 8){
                    password.setError("Password must be 8 characters long and contain a number");
                    return;
                }
                if((!spass.contains("0"))&&(!spass.contains("1"))&&(!spass.contains("2"))&&(!spass.contains("3"))&&(!spass.contains("4"))&&(!spass.contains("5"))&&(!spass.contains("6"))&&(!spass.contains("7"))&&(!spass.contains("8"))&&(!spass.contains("9"))){
                    password.setError("Password must contain number");
                }
                if(!spass.contentEquals(cspass)){
                    cpassword.setError("conform password is not equal to password!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // register user now
                firebaseAuth.createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //update user details on database
                            regHelperclass helperclass = new regHelperclass(sname,semail,smob,spass,gender,firebaseAuth.getUid());
                            reference.child(firebaseAuth.getUid()).child("personal details").setValue(helperclass);
                            reference.child(firebaseAuth.getUid()).child("enrolled").setValue(null);
                            Toast.makeText(RegistrationActivity.this,"Registered succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegistrationActivity.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        regtowel=(FloatingActionButton) findViewById(R.id.regtowelcome);
        regtowel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}


