//package com.example.loginform;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.SpannableString;
//import android.text.style.UnderlineSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import administratorpkg.Addministrator_0r_Teacher_Login;
//import studentpakage.student_panel;
//
//public class MainActivity extends AppCompatActivity {
//
//
//    TextView forgotPasswordLink,teacher_login;
//    SpannableString content;
//    Button login;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        login=findViewById(R.id.button);
//        forgotPasswordLink=findViewById(R.id.forgot);
//        teacher_login=findViewById(R.id.teacherlogin);
//         content = new SpannableString("Forgot Password?");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        forgotPasswordLink.setText(content);
//        content = new SpannableString("Administrator\\Teacher Login");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        teacher_login.setText(content);
//        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
//            }
//        });
//        teacher_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Addministrator_0r_Teacher_Login.class));
//            }
//        });
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this, student_panel.class);
//                startActivity(intent);
//            }
//
//        });
//
//    }
//}