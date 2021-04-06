package com.example.assignmentfirst;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_Fragment extends Fragment {

    EditText email_forget;
    Button button_forget;
    FirebaseAuth auth;
    NavController navController;

    public forgot_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(getActivity(),R.id.host_fragment);
        email_forget = (EditText) view.findViewById(R.id.forget_email);
        button_forget = view.findViewById(R.id.forget_button);




        button_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailReset = (String) email_forget.getText().toString();

                if(!checkEmptyField())
                {
                    FirebaseAuth.getInstance()
                            .sendPasswordResetEmail(emailReset)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity().getApplicationContext(), "Password Reset link send to\n"+emailReset , Toast.LENGTH_SHORT).show();
                                        navController.navigate(R.id.login_Fragment);
                                    }
                                }
                            });
                }
            }
        });



    }

    private boolean checkEmptyField() {

        if(TextUtils.isEmpty(email_forget.getText().toString()))
        {
            email_forget.setError("Enter Email");
            email_forget.requestFocus();
            return true;
        }
        return false;
    }
}