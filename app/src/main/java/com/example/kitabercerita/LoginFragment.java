package com.example.kitabercerita;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitabercerita.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    private EditText Username, Password;
    private Button Login;
    FirebaseDatabase db;
    DatabaseReference rf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        Username = view.findViewById(R.id.Username);
        Password = view.findViewById(R.id.editTextTextPassword2);
        Login = view.findViewById(R.id.LoginBtn);

//        Button goToRegisterButton = view.findViewById(R.id.toRegister);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = Username.getText().toString();
                String password = Password.getText().toString();

                db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rf = db.getReference("User");
                if(isValidLogin(userName,password)){

                    rf.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(userName)){
                                String getPassword = snapshot.child(userName).child("password").getValue(String.class);
                                if (password.equals(getPassword)){
                                    String email = snapshot.child(userName).child("email").getValue(String.class);
                                    String status = snapshot.child(userName).child("status").getValue(String.class);
                                    String phoneNumber = snapshot.child(userName).child("phoneNumber").getValue(String.class);
                                    String image = snapshot.child(userName).child("image").getValue(String.class);
                                    Toast.makeText(getActivity().getApplicationContext(), "Success!, you are now logged in!", Toast.LENGTH_SHORT).show();
                                    User.setCurrentUser(new User(userName, email, password, status, phoneNumber, image));
                                    Intent intent = new Intent(getActivity().getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getActivity().getApplicationContext(), "error...! Username or password incorrect!!", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), "error...! Username or password incorrect!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        return view;
    }

    private boolean isValidLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}