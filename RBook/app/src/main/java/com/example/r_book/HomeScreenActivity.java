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
        firebaseAuth = FirebaseAuth.getInstance();    /*değer atadık*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        listView = findViewById(R.id.listView);   //create listview
        listView.setAdapter(new PostAdapter(this, posts));   //adapter object

        Button btnPost = findViewById(R.id.btnPost);   /*post button on click*/
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, PostActivity.class);  /*post activitye geçiş yap*/
                startActivityForResult(intent, POST_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {   //they recieved named data
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POST_REQUEST && resultCode == Activity.RESULT_OK){
            Post post = new Post();
            post.setName(data.getCharSequenceExtra("name").toString());
            post.setPrice(data.getCharSequenceExtra("price").toString());
            post.setOther(data.getCharSequenceExtra("other").toString());
            post.setImage((Bitmap) data.getParcelableExtra("bitmap"));
            posts.add(post);
            ((PostAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*menuyü bağlamak için */

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.rbook_options_menu,menu); /*birbirine bağladık */

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  /*menuyü işlemleri gerçekleştirmek için */
        if(item.getItemId()==R.id.signout){
            firebaseAuth.signOut();  /*çıkış işlemi */
            Intent intent= new Intent(HomeScreenActivity.this,TabbedActivity.class); /*çıkınca başa dön*/
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}