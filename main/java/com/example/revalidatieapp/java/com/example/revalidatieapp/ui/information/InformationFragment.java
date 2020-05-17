package com.example.revalidatieapp.ui.information;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.revalidatieapp.NavigationInterface;
import com.example.revalidatieapp.R;

public class InformationFragment extends Fragment {

    //private InformationViewModel informationViewModel;
    private String TAG = "InformationFragment";
    private String userId;
    private NavigationInterface navigationInterface;

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //informationViewModel = ViewModelProviders.of(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        TextView title = root.findViewById(R.id.title0);
        TextView body = root.findViewById(R.id.body0);
        Button button = root.findViewById(R.id.button0);
        userId = navigationInterface.getUserId();
/*
        informationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/
        Resources res = getResources();

        title.setText("Informatie over uw gehele behandeling");
        body.setText("Hier vind u informatie over uw gehele behandeling");
        button.setText("Bekijk >");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fragmentId = R.id.fragment_folders;
                Log.d(TAG, "this is the fragmentId:" + fragmentId);
                navigationInterface.switchFragment(fragmentId);
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
