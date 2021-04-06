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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;


public class Login_Fragment extends Fragment {

    EditText email_login,password_login;
    TextView register,forgot;
    Button loginBtn;

    public NavController navController;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    public Login_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        user = firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            updateUI(user);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        email_login = view.findViewById(R.id.email_Login_Fragment);
        password_login = view.findViewById(R.id.password_Login_Fragment);
        loginBtn = view.findViewById(R.id.logIn_button_Login_Fragment);
        register = view.findViewById(R.id.register_Login_Fragment);
        forgot = view.findViewById(R.id.forgot_password_Login_Fragment);

        navController = Navigation.findNavController(getActivity(),R.id.host_fragment);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.register_Fragment);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.forgot_Fragment);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkEmptyField())
                {
                    String user_email = email_login.getText().toString();
                    String user_password = password_login.getText().toString();
                    loginUser(user_email,user_password);

                }

            }
        });
    }

    private void loginUser(String user_email, String user_password) {

        firebaseAuth.signInWithEmailAndPassword(user_email,user_password)
                .addOnCompleteListener(getActivity(),task -> {

                    if(task.isSuccessful())
                    {
                        user = firebaseAuth.getCurrentUser();
                        Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        updateUI(user);
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Login Failed !!!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateUI(FirebaseUser user) {

        Intent intent = new Intent(getContext(), MainActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public boolean checkEmptyField()
    {
        if(TextUtils.isEmpty(email_login.getText().toString()))
        {
            email_login.setError("email required***");
            email_login.requestFocus();
            return true;
        }
        else if(TextUtils.isEmpty(password_login.getText().toString()))
        {
            password_login.setError("password required***");
            password_login.requestFocus();
            return true;
        }

        return false;
    }

}