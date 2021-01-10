package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    List<Post> posts = new ArrayList<>();
    ListView listView;
    static final int POST_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();    /* we set a value */
        userNameFromFB = new ArrayList<>();
        userPriceFromFB = new ArrayList<>();
        userOtherFromFB = new ArrayList<>();
        userEmailFromFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getDataFromFirestore();

        listView = findViewById(R.id.listView);
        listView.setAdapter(new PostAdapter(this, posts));

        Button btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, PostActivity.class);
                startActivityForResult(intent, POST_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POST_REQUEST && resultCode == Activity.RESULT_OK){
            Post post = new Post();
            post.setMessage(data.getCharSequenceExtra("msg").toString());
            post.setImage((Bitmap) data.getParcelableExtra("bitmap"));
            posts.add(post);
            ((PostAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/* to link the menu */

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.rbook_options_menu,menu); /* linking is done. */
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  /* To perform menu operations */
        if(item.getItemId()==R.id.signout){
            firebaseAuth.signOut();  /*sign out process */
            Intent intent= new Intent(HomeScreenActivity.this,TabbedActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    public void getDataFromFirestore(){

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");

        collectionReference.orderBy("date", DownloadManager.Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(HomeScreen.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String,Object> data = snapshot.getData();

                        //Casting

                        String name = (String) data.get("bookname");
                        String price = (String) data.get("bookprice");
                        String other = (String) data.get("bookotherinfo");
                        String userEmail = (String) data.get("useremail");
                        String downloadUrl = (String) data.get("downloadurl");

                        userNameFromFB.add(name);
                        userPriceFromFB.add(price);
                        userOtherFromFB.add(other);
                        userEmailFromFB.add(userEmail);
                        userImageFromFB.add(downloadUrl);

                        /*feedRecyclerAdapter.notifyDataSetChanged();*/

                    }


                }

            }
        });


    }
}
