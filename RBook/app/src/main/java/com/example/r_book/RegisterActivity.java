package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegesterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regester);

        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        emailText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.txtpassword);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(RegesterActivity.this, HomeActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

    }

    public void loginClicked(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegesterActivity.this, "User Regested", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegesterActivity.this, HomeActivity.class); /*başarılı olunca gideceği yer*/
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegesterActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show(); /*message*/

            }

        });

    }

    public void regesterClicked(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        Intent intent=new Intent(RegesterActivity.this,LoginActivity.class); /*regester basınca login ekranına gidecek*/
        startActivity(intent);

    }
}
