package com.example.kitabercerita;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private EditText Email, Password, Username, Phonenumber;
    private Button buttonReg;
    FirebaseDatabase db;
    DatabaseReference rf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false) ;
        Email = view.findViewById(R.id.editTextTextEmailAddress);
        Password = view.findViewById(R.id.editTextTextPassword);
        Username = view.findViewById(R.id.Username);
        Phonenumber = view.findViewById(R.id.PhoneNumber);
        buttonReg = view.findViewById(R.id.buttonReg);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click
                Log.d("Register", "clicked");
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String username = Username.getText().toString();
                String phoneNumber = Phonenumber.getText().toString();
                Log.d("Register", email + password + username + phoneNumber);
                if (isValidRegister(username, email, password, phoneNumber)) {
                    Log.d("Register", "valid");
                    db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    rf = db.getReference("User");
                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("password", password);
                    userMap.put("phoneNumber", phoneNumber);
                    userMap.put("image", "blankpp");
                    userMap.put("status", "No Status");

                    rf.child(username).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Z", "Post successfully sent");
                            Toast.makeText(getActivity().getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.authFragment, new LoginFragment());
                            ft.commit();
                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Registration invalid!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private boolean isValidRegister(String username, String email, String password, String phoneNumber) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phoneNumber.isEmpty();
    }
}