package com.example.loginform;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import administratorpkg.administrotor_panel;
import administratorpkg.Addministrator_0r_Teacher_Login;
import studentpakage.student_panel;
import teacherpkg.teacher_panel;

public class student_login extends AppCompatActivity {

    TextView forgotPasswordLink, teacher_login;
    SpannableString content;
    Button login;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;
    private FrameLayout overlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        login = findViewById(R.id.button);
        forgotPasswordLink = findViewById(R.id.forgot);
        teacher_login = findViewById(R.id.teacherlogin);
        content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordLink.setText(content);
        content = new SpannableString("Administrator\\Teacher Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        teacher_login.setText(content);

        overlay = findViewById(R.id.overlay);
        progressBar = findViewById(R.id.progressBar);


        // Hide UI elements initially
        login.setVisibility(View.GONE);
        forgotPasswordLink.setVisibility(View.GONE);
        teacher_login.setVisibility(View.GONE);

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_login.this, ForgotPasswordActivity.class));
            }
        });
        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_login.this, Addministrator_0r_Teacher_Login.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                overlay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(student_login.this, student_panel.class);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
            }

        });


        // Check if the user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Show loading indicator while fetching user type
            overlay.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            // User is already logged in, redirect to the appropriate home page
            navigateToHomePage(currentUser);
        } else {
            // Hide the progress bar
            overlay.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            // Show login UI elements if the user is not logged in
            login.setVisibility(View.VISIBLE);
            forgotPasswordLink.setVisibility(View.VISIBLE);
            teacher_login.setVisibility(View.VISIBLE);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to the appropriate home page
            navigateToHomePage(currentUser);
        }
    }

    private void navigateToHomePage(@NonNull FirebaseUser user) {
        String uid = user.getUid();
        getUserType(uid, userType -> {
            if ("Teacher".equals(userType)) {

                startActivity(new Intent(getApplicationContext(), teacher_panel.class));
                finish();
            } else if ("Admin".equals(userType)) {

                startActivity(new Intent(getApplicationContext(), administrotor_panel.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), student_login.class));
            }
            finish();
        });
    }

    public void getUserType(String uid, UserTypeCallback callback) {
        Log.d(TAG, "getUserType method called");

        DocumentReference df = fstore.collection("user").document(uid);
        df.get().addOnSuccessListener(documentSnapshot -> {
            String userType = "Unknown";
            if (documentSnapshot.exists()) {
                if (documentSnapshot.getBoolean("isTeacher") != null && documentSnapshot.getBoolean("isTeacher")) {
                    userType = "Teacher";
                } else if (documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isAdmin")) {
                    userType = "Admin";
                }
            } else {
                Log.d(TAG, "Document does not exist");
            }
            callback.onUserTypeReceived(userType);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error retrieving user type: ", e);
            callback.onUserTypeReceived("Unknown");
        });
    }

    // Define an interface for callback
    public interface UserTypeCallback {
        void onUserTypeReceived(String userType);
    }
}
