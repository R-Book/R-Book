package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    static final int CAPTURE_IMAGE = 0;
    static final int REQUEST_READ_STORAGE = 1;

    Bitmap selectedImage;
    ImageView img;
    Button btnOk;
    Button btnCancel;
    EditText txtName,txtPrice,txtOther;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imageData;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post2);

        img = findViewById(R.id.imageView);   /* imageView in row.xml*/
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        txtName = findViewById(R.id.txtname);
        txtPrice = findViewById(R.id.txtprice);
        txtOther = findViewById(R.id.txtother);

        firebaseStorage = FirebaseStorage.getInstance();   /*initialize firebase storage*/
        storageReference = firebaseStorage.getReference();   /*we give reference.*/
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnOk.setOnClickListener(new View.OnClickListener() {   /*when you click to ok button*/
            @Override
            public void onClick(View view) {

                if (imageData != null) {

                    UUID uuid = UUID.randomUUID();   //universal unquie id uui
                    final String imageName = "images/" + uuid + ".jpg";

                    storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {   //firebase de url a koy
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Download URL
                            StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                            newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    String downloadUrl = uri.toString(); /*I got the url*/

                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    String userEmail = firebaseUser.getEmail(); /*I got the email*/

                                    String bookname = txtName.getText().toString(); /*I got the book name*/
                                    String price = txtPrice.getText().toString(); /*I got the book price*/
                                    String other = txtOther.getText().toString(); /*I got the book other information*/

                                    HashMap<String, Object> postData = new HashMap<>(); //Bundle bundle = new Bundle();
                                    postData.put("useremail", userEmail);
                                    postData.put("downloadurl", downloadUrl);
                                    postData.put("name", bookname);
                                    postData.put("price", price);
                                    postData.put("other", other);
                                    postData.put("date", FieldValue.serverTimestamp());

                                    firebaseFirestore.collection("Posts").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent intent = new Intent(PostActivity.this, HomeScreenActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); /*turn off everything in the background.*/
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) { /*error message*/
                                            Toast.makeText(PostActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PostActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {/*when you tap to camera image*/
            @Override
            public void onClick(View v) {

               //for galery Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                // for camera MediaStore.ACTION_IMAGE_CAPTURE

                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAPTURE_IMAGE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null ) { /*if allowed and data is not empty*/
             imageData = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28) {   /*for android version*/
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    img.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    img.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
