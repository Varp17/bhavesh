package com.example.loginform;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import administratorpkg.Addministrator_0r_Teacher_Login;
import studentpakage.student_panel;
import teacherpkg.teacher_panel;

public class student_login extends AppCompatActivity {

    TextView forgotPasswordLink,teacher_login;
    SpannableString content;
    Button login;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.button);
        forgotPasswordLink=findViewById(R.id.forgot);
        teacher_login=findViewById(R.id.teacherlogin);
        content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordLink.setText(content);
        content = new SpannableString("Administrator\\Teacher Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        teacher_login.setText(content);
        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_login.this, ForgotPasswordActivity.class));
            }
        });
        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_login.this,  Addministrator_0r_Teacher_Login.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(student_login.this, student_panel.class);
                startActivity(intent);
            }

        });
    }}


