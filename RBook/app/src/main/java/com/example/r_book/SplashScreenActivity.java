package com.example.r_book;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //thread

        Thread thread = new Thread(){

            public void run(){
                try{
                    sleep(3000);

                }catch (Exception e){

                }finally{

                }
            }
        };

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(TabbedActivity.class)
                //.withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#1a1b29"))
                .withHeaderText("  ")
                .withFooterText("  ")
                .withBeforeLogoText(" WELCOME ")
                .withAfterLogoText("R-BOOK")
                .withLogo(R.drawable.icon);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        config.getBeforeLogoTextView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
