package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText usernameText,emailtxt, passwordText,passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        usernameText = findViewById(R.id.usernameText);
        emailtxt = findViewById(R.id.emailtxt);
        passwordText = findViewById(R.id.txtpassword);
        passwordtxt = findViewById(R.id.passwordTxt);

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            Intent intent=new Intent(RegesterActivity.this,HomeActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

    }

    public void backClicked(View view) {
        String username=usernameText.getText().toString();
        String email=emailtxt.getText().toString();
        String password=passwordText.getText().toString();
        String confirmpassword=passwordtxt.getText().toString();

        Intent intent=new Intent(RegesterActivity.this,HomeActivity.class); /*kullanıcı istemezse direk home ekranına geçiş yapar*/
        startActivity(intent);


    }
    public void loginClicked(View view) {
        String username=usernameText.getText().toString();
        String email=emailtxt.getText().toString();
        String password=passwordText.getText().toString();
        String confirmpassword=passwordtxt.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"User Regested",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegesterActivity.this,HomeActivity.class); /*başarılı olunca gideceği yer,regester olunca homescreene gider*/
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegesterActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show(); /*message*/

            }

        });
    }
    }

