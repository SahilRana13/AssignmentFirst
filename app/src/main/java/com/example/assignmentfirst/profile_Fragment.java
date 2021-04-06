package com.example.assignmentfirst;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class profile_Fragment extends Fragment {

    TextView name_profile, email_profile, gender_profile, city_profile, birthdate_profile;
    //FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // firebaseUser = getArguments().getParcelable("User");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        name_profile = view.findViewById(R.id.profile_name);
        email_profile = view.findViewById(R.id.profile_email);
        gender_profile = view.findViewById(R.id.profile_gender);
        birthdate_profile = view.findViewById(R.id.profile_birthdate);
        city_profile = view.findViewById(R.id.profile_city);

        readFireStoreData();
    }

    private void readFireStoreData() {

        DocumentReference docRef = firebaseFirestore.collection("User")
                .document(firebaseAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();

                if(doc.exists())
                {
                    name_profile.setText((String)doc.get("Name"));
                    email_profile.setText((String)doc.get("Email"));
                    gender_profile.setText((String)doc.get("Gender"));
                    birthdate_profile.setText((String) doc.get("Birthdate"));
                    city_profile.setText((String)doc.get("City"));
                }
                else{}
            }
        });
    }
}