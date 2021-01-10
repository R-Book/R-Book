package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Bitmap addImage;
    ImageView imageView;
    EditText txtname,txtprice,txtother;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Uri imageData;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        imageView=findViewById(R.id.imageButton);
        txtname=findViewById(R.id.txtname);
        txtprice=findViewById(R.id.txtprice);
        txtother=findViewById(R.id.txtother);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();



    }

    public void share(View view){
        if(imageData!=null){
            /*universal unquie id uui*/
            UUID uuid=UUID.randomUUID();
            final String imageName="images/"+uuid+"jpng";
            storageReference.child("imageName").putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Download URL

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString(); /* I got the url */

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userEmail = firebaseUser.getEmail(); /*I got the email */

                            String bookname = txtname.getText().toString(); /*I got the book name*/
                            String price = txtprice.getText().toString(); /*I got the book price*/
                            String other = txtother.getText().toString(); /*I got the book other information*/

                            HashMap<String, Object> postData = new HashMap<>();
                            postData.put("useremail",userEmail);
                            postData.put("downloadurl",downloadUrl);
                            postData.put("bookname",bookname);
                            postData.put("bookprice",price);
                            postData.put("bookotherinfo",other);
                            postData.put("date", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Intent intent = new Intent(AddImageActivity.this,HomeScreenActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); /* turn off everything in the background */
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) { /*error message*/
                                    Toast.makeText(AddImageActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });




                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddImageActivity.this,e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }




    }
    public void addImage(View view){  /* everything that should happen when pressing add image for example: permissions */
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }else{
            Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { /* Things are done to see what will happen after getting permission */
        if (requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode ==RESULT_OK && data != null) {  /* The answer to the question of what will happen if image is selected and completed */
            imageData = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28) { /* to check android version */
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    addImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(addImage);

                } else {
                    addImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    imageView.setImageBitmap(addImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
