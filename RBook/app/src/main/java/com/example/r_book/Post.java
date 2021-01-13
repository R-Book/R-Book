package com.example.r_book;

import android.graphics.Bitmap;

public class Post {

    private Bitmap image;
    private String name;
    private String price;
    private String other;

    public Bitmap getImage() {
            return image;
    }

    public void setImage(Bitmap image) {
            this.image = image;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        }

    public String getOther(){
        return name;
    }

    public void setOther(String other){
        this.other = other;
    }

}

