package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class maximizeimage extends AppCompatActivity {

    ImageView scheduleimg;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maximizeimage);
        scheduleimg=(ImageView) findViewById(R.id.imageView);
        intent=getIntent();
        int id=intent.getIntExtra("img",0);
        scheduleimg.setImageResource(id);
    }
}