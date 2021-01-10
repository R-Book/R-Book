package com.example.r_book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;


import java.util.List;

public class PostAdapter extends BaseAdapter {

    List<Post> posts;  //constructor
    LayoutInflater inflater;

    public PostAdapter(Activity activity, List<Post> posts) {
        this.posts = posts;
        inflater = activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return posts.size();  //it depends out data
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, null);  /*row source*/

        EditText txtName = (EditText) rowView.findViewById(R.id.txtname);
        EditText txtPrice = (EditText) rowView.findViewById(R.id.txtprice);
        EditText txtOther = (EditText) rowView.findViewById(R.id.txtother);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        Post post = posts.get(position);

        txtName.setText(post.getName());
        txtPrice.setText(post.getPrice());
        txtOther.setText(post.getOther());
        imageView.setImageBitmap(post.getImage());


        if (post.getName() != null) {
            txtName.setText(post.getName()+ " " + post.getName());
        }
        return rowView;
    }
}