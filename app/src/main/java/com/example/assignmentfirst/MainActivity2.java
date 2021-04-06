package com.example.assignmentfirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;
    FirebaseUser user;
    FirebaseAuth auth;


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("abc","start called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("abc","Resume called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("abc","stop called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        setupNavigationView();

    }

    public  void setupNavigationView()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(MainActivity2.this,R.id.host_fragment_activity_main2);

        NavigationUI.setupActionBarWithNavController(MainActivity2.this,navController,drawerLayout);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationView.setNavigationItemSelectedListener(MainActivity2.this);

    }



    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.host_fragment_activity_main2),drawerLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setCheckable(true);
        drawerLayout.closeDrawers();

        int id = item.getItemId();
        switch (id)
        {
            case R.id.profile:
                Toast.makeText(getApplicationContext(),"Profile Page",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.profile_Fragment);
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
        }
        return true;
    }



}