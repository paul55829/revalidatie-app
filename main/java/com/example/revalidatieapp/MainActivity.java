package com.example.revalidatieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText editTextUsername;
    private EditText editTextPassword;

    //private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        editTextUsername = findViewById(R.id.edit_username);
        editTextPassword = findViewById(R.id.edit_password);
        Log.d(TAG, "test(1) einde oncreate mainActivity");
    }

    public void login(View v){
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put("username", username);
        note.put("password", password);

        usersRef.whereEqualTo("username", username).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int i = 0;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            i++;
                            String correctPassword = documentSnapshot.getString("password");
                            if (password.equals(correctPassword)){
                                documentSnapshot.getId();
                                Intent startIntent = new Intent(getApplicationContext(), Navigation.class);
                                startIntent.putExtra("com.example.revalidatieapp.USERID", documentSnapshot.getId());
                                startActivity(startIntent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (i == 0){
                            Toast.makeText(MainActivity.this, "username is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
