package com.example.r_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        Button Register = findViewById(R.id.register_button);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);
        
         if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class); /*başarılı olunca gideceği yer*/
            startActivity(intent);
        }

    }

        Register.setOnClickListener(view -> {
            String mail = Email.getText().toString();
            String password = Password.getText().toString();
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
        });
    }
    public void loginClicked(View view) {   /*login button*/
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "User Regested", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class); /*başarılı olunca gideceği yer*/
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show(); /*message*/

            }

        });

    }

    public void regesterClicked(View view) {  /*regester button*/
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        Intent intent=new Intent(LoginActivity.this,RegesterActivity.class); /*regester basınca login ekranına gidecek*/
        startActivity(intent);

    }


}


