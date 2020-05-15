package com.example.revalidatieapp.ui.messages;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.revalidatieapp.NavigationInterface;
import com.example.revalidatieapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    private NavigationInterface navigationInterface;
    private MessagesViewModel messagesViewModel;
    private String userId;
    private ListView theListView;
    private String TAG = "MessagesFragment";
    private ArrayList<Message> messages;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



//    public interface SwitchFragment{
//        void switchFragment(Fragment fragment);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        theListView = (ListView) root.findViewById(R.id.listView);
        /*

        messagesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        ArrayList<Message> messages = new ArrayList<>();
        try{
            userId = getArguments().getString("com.example.revalidatieapp.USERID");
        }
        catch(Exception e){
            userId = messagesViewModel.getUserId();
        }

        db.collection("users").document(userId).collection("messages").orderBy("date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "nu begint de tweede onsucces");
                        ArrayList<Message> messages = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String subject = documentSnapshot.getString("subject");
                            String from = documentSnapshot.getString("from");
                            String time = documentSnapshot.getString("time");
                            String id = documentSnapshot.getId();
                            boolean opened = documentSnapshot.getBoolean("opened");

                            Message extraMessage = new Message();
                            extraMessage.setFrom(from);
                            extraMessage.setSubject(subject);
                            extraMessage.setTime(time);
                            extraMessage.setId(id);
                            extraMessage.setOpened(opened);
                            messages.add(extraMessage);

                        }
                        setMessages(messages);
                        MessageAdapter messageAdapter = new MessageAdapter(getContext(), messages);
                        theListView.setAdapter(messageAdapter);
                    }
                });

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = getMessages().get(position);
                String theId = message.getId();

                MessageFragment messageFragment = new MessageFragment();
                Bundle args = new Bundle();
                String[] info = {theId, userId};
                args.putStringArray("com.example.revalidatieapp.INFO", info);
                messageFragment.setArguments(args);

                navigationInterface.switchFragment(messageFragment);

            }
        });

        return root;
    }

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

    public void setMessages(ArrayList<Message> theMessages){
        messages = theMessages;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }
}
