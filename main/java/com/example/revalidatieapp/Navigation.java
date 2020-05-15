package com.example.revalidatieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.revalidatieapp.ui.agenda.AgendaFragment;
import com.example.revalidatieapp.ui.information.InformationFragment;
import com.example.revalidatieapp.ui.messages.MessagesFragment;
import com.example.revalidatieapp.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navigation extends AppCompatActivity implements NavigationInterface {

private String userId;
private String TAG = "Navigation";
//private NavigationViewModel navigationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "test(1) hier begint onCreate Navigation");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "test(1) nu word de juiste layout toegepast");
        setContentView(R.layout.activity_navigation);
        //navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        Log.d(TAG, "dit is navigation");
        //is import for the OnNavigationItemSelectedListener
        Log.d(TAG, "test(1) nu word referentie bottomNav aangemaakt");
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //gets userId
        Intent intend = getIntent();
        Bundle bundle = intend.getExtras();
        userId = (String) bundle.get("com.example.revalidatieapp.USERID");
        //navigationViewModel.setUserId(userId);
        //userId = navigationViewModel.getUserId();

        Log.d(TAG, "test(1) nu word informationFragment aangemaakt");
        InformationFragment informationFragment = new InformationFragment();

        //gives userId to informationFragment
        Bundle args = new Bundle();
        args.putString("com.example.revalidatieapp.USERID", userId);
        informationFragment.setArguments(args);

        Log.d(TAG, "test(1) nu begint switchFragment");
        switchFragment(informationFragment);

        Log.d(TAG, "test(1) einde oncreate navigation");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_information:
                            selectedFragment = new InformationFragment();
                            break;
                        case R.id.navigation_messages:
                            selectedFragment = new MessagesFragment();
                            break;
                        case R.id.navigation_agenda:
                            selectedFragment = new AgendaFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    //gives userID to selectdFragment
                    Bundle args = new Bundle();
                    args.putString("com.example.revalidatieapp.USERID", userId);
                    selectedFragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();

                    return true;
                }


            };

    public void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
    }

}
