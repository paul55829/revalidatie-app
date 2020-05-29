package com.example.revalidatieapp.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.revalidatieapp.NavigationInterface;
import com.example.revalidatieapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment{

//    private ProfileViewModel profileViewModel;
    private NavigationInterface navigationInterface;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Switch switchReminders;
    private String userId;
    private TextView timeTextField;
    private String TAG = "ProfileFragment";
    int practiceTimeIndex;
    int restTimeIndex;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        userId = navigationInterface.getUserId();
//        final TextView textView = root.findViewById(R.id.text_profile);
//        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //when the fragment is created, the setonItemListener is activated and position 0 is given as parameter.
        //this causes the practiceTime and restTime to be updated to a wrong value.
        //when the fragment is created practiceTimeIndex and restTimeIndex are 0
        // At the end of onItemSelectedListener the index is increased by 1 so when the index is 0 everything inside the listener shoudn't be executed.
        practiceTimeIndex = 0;
        restTimeIndex = 0;

        final Spinner spinnerPracticeTime = (Spinner) root.findViewById(R.id.spinnerPracticeTime);
        ArrayAdapter<CharSequence> adapterPracticeTime = ArrayAdapter.createFromResource(getContext(), R.array.spinner_practice_time, android.R.layout.simple_spinner_item);
        adapterPracticeTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPracticeTime.setAdapter(adapterPracticeTime);

        spinnerPracticeTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: practiceTimeIndex = " + practiceTimeIndex);
                if(practiceTimeIndex != 0){
                    String practiceTimeString = parent.getItemAtPosition(position).toString();
                    int practiceTimeInteger =  Integer.parseInt(practiceTimeString);
                    HashMap<String, Object> update = new HashMap<>();
                    update.put("practiceTime", practiceTimeInteger);
                    db.collection("users").document(userId).update(update);
                    Log.d(TAG, "onItemSelected: update = " + update);
                }
                else{
                    db.collection("users").document(userId).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    int practiceTimeDatabase = Integer.parseInt(documentSnapshot.getLong("practiceTime").toString());
                                    String[] practiceTimesString= getResources().getStringArray(R.array.spinner_practice_time);

                                    for(int i = 0; i < practiceTimesString.length;i++){
                                        int practiceTimeInt = Integer.parseInt(practiceTimesString[i]);
                                        if(practiceTimeInt == practiceTimeDatabase){
                                            spinnerPracticeTime.setSelection(i);
                                        }
                                    }
                                }
                            });
                }
                practiceTimeIndex++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner spinnerRestTime = (Spinner) root.findViewById(R.id.spinnerRestTime);
        ArrayAdapter<CharSequence> adapterRestTime = ArrayAdapter.createFromResource(getContext(), R.array.spinner_rest_time, android.R.layout.simple_spinner_item);
        adapterRestTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestTime.setAdapter(adapterRestTime);

        spinnerRestTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: restTimeIndex = " + restTimeIndex);
                if (restTimeIndex != 0){
                    String restTime = parent.getItemAtPosition(position).toString();
                    HashMap<String, Object> update = new HashMap<>();
                    update.put("restTime", restTime);
                    db.collection("users").document(userId).update(update);
                    Log.d(TAG, "onItemSelected: update = " + update);
                }
                else{
                    db.collection("users").document(userId).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String restTimeDatabase = documentSnapshot.getString("restTime");
                                    String[] restTimeStrings= getResources().getStringArray(R.array.spinner_rest_time);

                                    for(int i = 0; i < restTimeStrings.length;i++){
                                        String restTimeString = restTimeStrings[i];
                                        if(restTimeString.equals(restTimeDatabase)){
                                            spinnerRestTime.setSelection(i);
                                        }
                                    }
                                }
                            });
                }
                restTimeIndex++;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        switchReminders = (Switch) root.findViewById(R.id.switchReminders);
        timeTextField = (TextView) root.findViewById(R.id.timeTextField);


        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        boolean receiveReminders = documentSnapshot.getBoolean("receiveReminders");
                        if(receiveReminders) {
                            switchReminders.setChecked(true);
                            switchReminders.setText( R.string.notification_on);
                        }
                        else {
                            switchReminders.setChecked(false);
                            switchReminders.setText(R.string.notification_off);
                        }
                    }
                });


        switchReminders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switchReminders.setText(R.string.notification_on);
                    changeReceiveReminders(true);
                }
                else {
                    switchReminders.setText(R.string.notification_off);
                    changeReceiveReminders(false);
                }
            }
        });

        Button changeTimeButton = (Button) root.findViewById(R.id.changeTimeButton);
        changeTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
            }
        });


        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        db.collection("users").document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            try {
                                Map user = documentSnapshot.getData();
                                HashMap<String, String> bedtime = (HashMap<String, String>) user.get("bedtime");
                                String hour = bedtime.get("hour");
                                String minutes = bedtime.get("minute");

                                Log.d(TAG, "test(5) dit is bedtime: " + bedtime);
                                timeTextField.setText("meldingen stoppen om " + hour + ":" + minutes);
                            }
                            catch(Exception exception){
                                timeTextField.setText("error: " + exception.toString());
                            }
                        }
                    }
                });
    }

    private void changeReceiveReminders(boolean receiveReminders){
        HashMap<String, Object> update = new HashMap<>();
        update.put("receiveReminders", receiveReminders);
        db.collection("users").document(userId).update(update);
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
