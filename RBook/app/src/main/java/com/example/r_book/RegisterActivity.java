package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameText,emailText, passwordText,passwordText2;
        Button Back = findViewById(R.id.back_button);
        Button Login = findViewById(R.id.login_button2);
        //Log.e("dev", "Burdayım3");   /*for debug*/

        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.emailtxt);
        passwordText = findViewById(R.id.passwordtxt);
        passwordText2 = findViewById(R.id.passwordtxt2);

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            Intent intent=new Intent(RegisterActivity.this,HomeScreenActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

        Back.setOnClickListener(view -> {
        Intent intent=new Intent(RegisterActivity.this,HomeScreenActivity.class); /*kullanıcı istemezse direk home ekranına geçiş yapar*/
        startActivity(intent);
    });

        Login.setOnClickListener(view -> {
        String username=usernameText.getText().toString();
        String email=emailText.getText().toString();
        String password=passwordText.getText().toString();
        String confirmpassword=passwordText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this,"User Registed",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegisterActivity.this,HomeScreenActivity.class); /*başarılı olunca gideceği yer,regester olunca homescreene gider*/
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() { /*başarısız olunca mesaj göster*/
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show(); /*message*/

            }

        });
        });
    }
}
