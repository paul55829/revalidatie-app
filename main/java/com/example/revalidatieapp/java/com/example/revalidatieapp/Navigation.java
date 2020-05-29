package com.example.revalidatieapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Navigation extends AppCompatActivity implements NavigationInterface, TimePickerDialog.OnTimeSetListener{

    private String userId;
    private String TAG = "Navigation";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_dashboard, R.id.navigation_information, R.id.navigation_messages, R.id.navigation_agenda, R.id.navigation_profile).build();
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);

        //gets userId
        Intent intend = getIntent();
        Bundle bundle = intend.getExtras();
        userId = (String) bundle.get("com.example.revalidatieapp.USERID");

    }

    public void switchFragment(int fragmentId, Bundle args){
        androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment).navigate(fragmentId, args);
    }

    public void switchFragment(int fragmentId){
        androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment).navigate(fragmentId);
    }

    public String getUserId(){
        return userId;
    }

    //handles the input from the timepicker of the profile fragment. this could only be done in the activity and not in the fragment itself.
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        HashMap<String, String> bedtime = new HashMap<>();
        HashMap<String, Object> update = new HashMap<>();
        bedtime.put("hour", TimeConverter.convertTime(hourOfDay));
        bedtime.put("minute", TimeConverter.convertTime(minute));
        update.put("bedtime", bedtime);
        db.collection("users").document(userId).update(update);
    }
}
