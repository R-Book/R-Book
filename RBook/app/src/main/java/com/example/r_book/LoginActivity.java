package com.example.r_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText Email = findViewById(R.id.username);
        EditText Password = findViewById(R.id.password);
        Button Register = findViewById(R.id.register_button);
        Button Login = findViewById(R.id.login_button);

        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

        Register.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });

        Login.setOnClickListener(view -> {
            String email = Email.getText().toString();
            String password = Password.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "User Registed", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class); /*başarılı olunca gideceği yer*/
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show(); /*message*/

                }
            });
        });
    }
}



