package com.example.assignmentfirst;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class Register_Fragment extends Fragment {

    EditText name,email,date,city,gender,password,rePassword;
    Button registerButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private NavController navController;

    public Register_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.register_name);
        email = view.findViewById(R.id.register_email);
        date = view.findViewById(R.id.register_birthdate);
        city = view.findViewById(R.id.register_city);
        gender = view.findViewById(R.id.register_gender);
        registerButton = view.findViewById(R.id.register_button);
        password = view.findViewById(R.id.register_password);
        rePassword = view.findViewById(R.id.register_re_password);

        navController = Navigation.findNavController(getActivity(),R.id.host_fragment);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkEmptyField())
                {
                    if(password.getText().length()<6)
                    {
                        password.setError("Invalid Password!!!\nPassword should be at least 6 characters long!");
                        password.requestFocus();
                    }
                    else
                    {
                        if(!password.getText().toString().equals(rePassword.getText().toString()))
                        {
                            rePassword.setError("Password Not Matched !!!");
                            rePassword.requestFocus();
                        }
                        else
                        {
                            String email_person = email.getText().toString();
                            String password_person = password.getText().toString();
                            String name_person = name.getText().toString();
                            String city_person = city.getText().toString();
                            String gender_person = gender.getText().toString();
                            String date_person = date.getText().toString();

                            Person person = new Person(email_person,password_person,name_person,city_person,gender_person,date_person);
                            createUser(person);
                        }
                    }
                }
            }
        });

    }

    public boolean checkEmptyField()
    {
        if(TextUtils.isEmpty(name.getText().toString()))
        {
            name.setError("enter name***");
            name.requestFocus();
            return true;
        }
        else if (TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError("enter email***");
            email.requestFocus();
            return true;
        }
        else if (TextUtils.isEmpty(city.getText().toString()))
        {
            city.setError("enter city***");
            city.requestFocus();
            return true;
        }
        else if(TextUtils.isEmpty(gender.getText().toString()))
        {
            gender.setError("enter gender***");
            gender.requestFocus();
            return true;
        }
        else if(TextUtils.isEmpty(date.getText().toString()))
        {
            date.setError("enter birthdate***");
            date.requestFocus();
            return true;
        }
        else if(TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("enter passwrod***");
            password.requestFocus();
            return true;
        }
        else if(TextUtils.isEmpty(rePassword.getText().toString()))
        {
            rePassword.setError("re-enter password***");
            rePassword.requestFocus();
            return true;
        }

        return false;
    }

    public void createUser(Person person)
    {
        firebaseAuth.createUserWithEmailAndPassword(person.getEmail(),person.getPassword())
                .addOnCompleteListener(getActivity(),task -> {
                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        writeFireStore(person,firebaseUser);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Registration Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void writeFireStore(Person person, FirebaseUser firebaseUser)
    {
        Map<String,Object> usermap = new HashMap<>();
        usermap.put("Name",person.getName());
        usermap.put("Email",person.getEmail());
        usermap.put("City",person.getCity());
        usermap.put("Gender",person.getGender());
        usermap.put("Birthdate",person.getDate());

        firestore.collection("User")
                .document(firebaseUser.getUid())
                .set(usermap)
                .addOnCompleteListener(getActivity(),task -> {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Registration Successful !", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        navController.navigate(R.id.login_Fragment);
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "FireStore Error!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}