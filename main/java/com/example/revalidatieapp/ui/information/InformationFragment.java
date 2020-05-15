package com.example.revalidatieapp.ui.information;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.revalidatieapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InformationFragment extends Fragment {

    private InformationViewModel informationViewModel;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private String username;

    private String TAG = "InformationFragment";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "test(1) nu begint OncreateView informationFragment");
        informationViewModel = ViewModelProviders.of(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        final TextView textView = root.findViewById(R.id.text_information);

/*
        informationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/

        if (getArguments() != null) {
            username = getArguments().getString("com.example.revalidatieapp.USERNAME");
            textView.setText(username);
        }
        return root;
    }
}
