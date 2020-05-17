package com.example.revalidatieapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navigation extends AppCompatActivity implements NavigationInterface {

private String userId;
private String TAG = "Navigation";
//private NavigationViewModel navigationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_information, R.id.navigation_messages, R.id.navigation_agenda, R.id.navigation_profile).build();
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);

        //gets userId
        Intent intend = getIntent();
        Bundle bundle = intend.getExtras();
        userId = (String) bundle.get("com.example.revalidatieapp.USERID");
        //navigationViewModel.setUserId(userId);
        //userId = navigationViewModel.getUserId();

//        InformationFragment informationFragment = new InformationFragment();

        //gives userId to informationFragment
//        Bundle args = new Bundle();
//        args.putString("com.example.revalidatieapp.USERID", userId);
//        informationFragment.setArguments(args);

//        switchFragment(informationFragment);

    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.navigation_information:
//                            selectedFragment = new InformationFragment();
//                            break;
//                        case R.id.navigation_messages:
//                            selectedFragment = new MessagesFragment();
//                            break;
//                        case R.id.navigation_agenda:
//                            selectedFragment = new AgendaFragment();
//                            break;
//                        case R.id.navigation_profile:
//                            selectedFragment = new ProfileFragment();
//                            break;
//                    }
//
//                    //gives userID to selectdFragment
//                    Bundle args = new Bundle();
//                    args.putString("com.example.revalidatieapp.USERID", userId);
//                    selectedFragment.setArguments(args);
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
//
//                    return true;
//                }
//
//
//            };

    public void switchFragment(int fragmentId, Bundle args){
        androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment).navigate(fragmentId, args);
    }

    public void switchFragment(int fragmentId){
        androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment).navigate(fragmentId);
    }

    public String getUserId(){
        return userId;
    }

}
