package com.example.duan1_nhom5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;



public class ManHinhChao extends AppCompatActivity {
    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);

        appname = findViewById(R.id.appname);
        lottie = findViewById(R.id.lottie);


        String animationUrl = "https://lottie.host/67bfabfe-8b9c-4032-a8c8-d109bb7ffdf4/RZKyUmc9OY.json";
        lottie.setAnimationFromUrl(animationUrl);
        lottie.playAnimation();

        appname.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottie.animate().translationY(2000).setDuration(2700).setStartDelay(2900);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManHinhChao.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3500);
    }
}