package com.example.revalidatieapp.ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.revalidatieapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InformationFragment extends Fragment {

    private InformationViewModel informationViewModel;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        informationViewModel = ViewModelProviders.of(this).get(InformationViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_information, container, false);
        final TextView textView = root.findViewById(R.id.text_information);
        informationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        /*
        Button googleBtn = (Button)root.findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String google = "http://www.google.com";
                Uri webadress = Uri.parse(google);

                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webadress);
                if (gotoGoogle.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(gotoGoogle);
                }
            }
        });
        */

        return root;
    }
}
