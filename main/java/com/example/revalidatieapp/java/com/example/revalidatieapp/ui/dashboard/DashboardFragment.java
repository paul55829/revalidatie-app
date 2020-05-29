package com.example.revalidatieapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.revalidatieapp.NavigationInterface;
import com.example.revalidatieapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewmodel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NavigationInterface navigationInterface;
    private static final String TAG = "DashboardFragment";

    private TextView goal1TextView;
    private TextView goal2TextView;
    private TextView firstNameTextView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewmodel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

//        final TextView textView = (TextView) root.findViewById(R.id.text_dashboard);
//        dashboardViewmodel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

//            @Override
//            public void onChanged(String s) {
//                textView.setText(s);
//            }
//        });
        firstNameTextView = (TextView) root.findViewById(R.id.firstNameTextView);
        goal1TextView = (TextView) root.findViewById(R.id.goal1);
        goal2TextView = (TextView) root.findViewById(R.id.goal2);
        TextView goalsTextView = (TextView) root.findViewById(R.id.goalsTextView);
        goalsTextView.setText(R.string.dashboard_goals);

                db.collection("users").document(navigationInterface.getUserId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String firstName = documentSnapshot.getString("firstName");
                        String greeting = getResources().getString(R.string.dashboard_greeting);
                        firstNameTextView.setText(greeting + " " +firstName + ",");

                        try {
                            Map user = documentSnapshot.getData();
                            HashMap<String, String> goals = (HashMap<String, String>) user.get("goals");
                            String goal1 = goals.get("goal1");
                            String goal2 = goals.get("goal2");

                            goal1TextView.setText("- " + goal1);
                            goal2TextView.setText("- " + goal2);
                        }
                        catch(Exception exception){
                            Log.d(TAG, "dit is de error: " + exception.toString());
                        }

                    }
                });





        return root;
    }

    //instantiates a Navigationinterface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NavigationInterface){
            navigationInterface = (NavigationInterface) context;
        }
        else{
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT);
        }
    }

}
