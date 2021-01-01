package com.example.r_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Register = findViewById(R.id.register_button);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);

        Register.setOnClickListener(view -> {
            String mail = Email.getText().toString();
            String password = Password.getText().toString();
            if (mail.isEmpty()) {
                Email.setError("Field can't be empty");
            }if (password.isEmpty()) {
                Password.setError("Field can't be empty");
            }
            else {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}