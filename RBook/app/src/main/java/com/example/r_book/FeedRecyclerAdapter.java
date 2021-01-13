package com.example.r_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> userEmailList;
    private ArrayList<String> NameList;
    private ArrayList<String> PriceList;
    private ArrayList<String> OtherList;
    private ArrayList<String> userImageList;


    public FeedRecyclerAdapter(ArrayList<String> userEmailList,ArrayList<String> OtherList,ArrayList<String> PriceList, ArrayList<String> NameList, ArrayList<String> userImageList) {
        this.userEmailList = userEmailList;
        this.NameList = NameList;
        this.PriceList = PriceList;
        this.OtherList = OtherList;
        this.userImageList = userImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.userEmailText.setText(userEmailList.get(position));
        holder.NameText.setText(NameList.get(position));
        holder.PriceText.setText(PriceList.get(position));
        holder.OtherText.setText(OtherList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userEmailText;
        TextView NameText;
        TextView PriceText;
        TextView OtherText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerview_row_imageview);
            userEmailText = itemView.findViewById(R.id.recyclerview_row_useremail_text);
            NameText = itemView.findViewById(R.id.recyclerview_row_name_text);
            PriceText = itemView.findViewById(R.id.recyclerview_row_price_text);
            OtherText = itemView.findViewById(R.id.recyclerview_row_other_text);


        }
    }
}
