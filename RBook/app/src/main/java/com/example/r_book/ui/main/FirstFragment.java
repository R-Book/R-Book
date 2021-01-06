package com.example.r_book.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.r_book.HomeScreenActivity;
import com.example.r_book.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.r_book.TabbedActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstFragment extends Fragment {

    PageViewModel pageViewModel;
    private FirebaseAuth firebaseAuth;

    public static FirstFragment newInstance(){
        return new FirstFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class); /*değer atadık*/

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_first,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText usernameText, emailText, passwordText, passwordText2;
        Button Back = view.findViewById(R.id.back_button);
        Button Login = view.findViewById(R.id.login_button2);
        //Log.e("dev", "Burdayım3");   /*for debug*/

        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        usernameText = view.findViewById(R.id.username);
        emailText = view.findViewById(R.id.emailtxt);
        passwordText = view.findViewById(R.id.passwordtxt);
        passwordText2 = view.findViewById(R.id.passwordtxt2);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(getActivity(), HomeScreenActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeScreenActivity.class); /*kullanıcı istemezse direk home ekranına geçiş yapar*/
                startActivity(intent);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmpassword = passwordText.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "User Registed", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), HomeScreenActivity.class); /*başarılı olunca gideceği yer,regester olunca homescreene gider*/
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() { /*başarısız olunca mesaj göster*/
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show(); /*message*/

                    }

                });

            }
        });
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pageViewModel.setName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}