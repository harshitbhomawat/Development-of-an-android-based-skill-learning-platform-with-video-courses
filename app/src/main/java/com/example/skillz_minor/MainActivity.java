package com.example.skillz_minor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.skillz_minor.ui.account.AccountsFragment;
import com.example.skillz_minor.ui.dashboard.DashboardFragment;
import com.example.skillz_minor.ui.home.HomeFragment;
import com.example.skillz_minor.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity{
    public static Context getContext;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    String sname;
    FragmentManager fragmentManager = getSupportFragmentManager();
    int f=1;
    boolean home = true;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    AccountsFragment accountsFragment = new AccountsFragment();
    FirebaseAuth firebaseAuth;
    Long totalEnrolled;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_nav_menu,menu);
        /*MenuItem searchItem = menu.findItem(R.id.navigation_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");

        Query query = reference.orderByChild("personal details/uid").equalTo(firebaseAuth.getUid());
        query.addListenerForSingleValueEvent(valueEventListener);
        Log.d(TAG, "onCreateView:" + sname);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        Toolbar toolbar = findViewById(R.id.mytoolbar);
//        setSupportActionBar(toolbar);
       // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
         //       R.id.navigation_home, R.id.navigation_search, R.id.navigation_dashboard, R.id.navigation_accounts)
           //     .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        getContext =MainActivity.this;
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_home:
                        if(f!=1){f=f+1;}
                        else{
                            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
                            home = true;
                            return true;}
                    case R.id.navigation_search:
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                        home = false;
                        return true;
                    case R.id.navigation_dashboard:
                       // AppBarConfiguration appBarConfiguration1 = new AppBarConfiguration.Builder(R.id.navigation_dashboard).build();
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, dashboardFragment).commit();
                        home = false;
                        return true;
                    case R.id.navigation_accounts:
                        Bundle bundle = new Bundle();
                        bundle.putString("sname",sname);
                        bundle.putString("number",String.valueOf(totalEnrolled));
                        accountsFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, accountsFragment).commit();
                        home = false;
                        return true;
                }
                return false;
            }
        });
        
    }
    @Override
    public void onBackPressed(){
        Fragment fragment= fragmentManager.findFragmentById(R.id.nav_host_fragment);
        if(fragment!=null){
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        else {
            super.onBackPressed();
        }
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                Log.d(TAG, "onDataChange:"+ snapshot.child(firebaseAuth.getUid()).child("personal details"));
                Log.d(TAG, "onDataChange: "+firebaseAuth.getUid());
                sname="exists";
                sname = snapshot.child(firebaseAuth.getUid()).child("personal details").child("name").getValue().toString();
                totalEnrolled = snapshot.child(firebaseAuth.getUid()).child("enrolled").getChildrenCount();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


}