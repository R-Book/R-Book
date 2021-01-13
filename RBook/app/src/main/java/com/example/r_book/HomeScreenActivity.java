package com.example.r_book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeScreenActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    List<Post> posts = new ArrayList<>();
    RecyclerView recyclerView;
    static final int POST_REQUEST = 1;

    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userPriceFromFB;
    ArrayList<String> userNameFromFB;
    ArrayList<String> userOtherFromFB;
    ArrayList<String> userImageFromFB;
    FeedRecyclerAdapter feedRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();    /*we assigned value*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        userNameFromFB = new ArrayList<>();
        userPriceFromFB = new ArrayList<>();
        userOtherFromFB = new ArrayList<>();
        userEmailFromFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();/*burdan tam emin değilim şimdilik dursun*/

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter = new FeedRecyclerAdapter(userEmailFromFB,userNameFromFB,userPriceFromFB,userOtherFromFB,userImageFromFB);
        recyclerView.setAdapter(feedRecyclerAdapter);

        Button btnPost = findViewById(R.id.btnPost);   /*post button on click*/
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, PostActivity.class);  /*Switch post activity*/
                startActivityForResult(intent, POST_REQUEST);
            }
        });
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {   //they recieved named data
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POST_REQUEST && resultCode == Activity.RESULT_OK){
            Post post = new Post();
            post.setName(data.getCharSequenceExtra("name").toString());
            post.setPrice(data.getCharSequenceExtra("price").toString());
            post.setOther(data.getCharSequenceExtra("other").toString());
            post.setImage((Bitmap) data.getParcelableExtra("bitmap"));
            posts.add(post);
            ((FeedRecyclerAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*to connect to the menu */

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.rbook_options_menu,menu); 

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  /*To perform menu operations. */
        if(item.getItemId()==R.id.signout){
            firebaseAuth.signOut();  /*sign out process */
            Intent intent= new Intent(HomeScreenActivity.this,TabbedActivity.class); /*back to top when you leave*/
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromFirestore(){

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(HomeScreenActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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

                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}



