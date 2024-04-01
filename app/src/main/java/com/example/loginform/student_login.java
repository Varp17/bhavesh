package com.example.loginform;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
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
    private EditText editTextUsername, editTextPassword;



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
        editTextPassword=findViewById(R.id.word);
        editTextUsername=findViewById(R.id.editTextText); // Fix this line


        overlay = findViewById(R.id.overlay);
        progressBar = findViewById(R.id.progressBar);

        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

//        Sprite wave = new Wave();
//        progressBar.setIndeterminateDrawable(wave);

//        Sprite x = new ThreeBounce();
//        progressBar.setIndeterminateDrawable(x);

        // Hide UI elements initially

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
            public void onClick(View v){



                loginUser();
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

        // Check if the user is deleted and handle accordingly
        if (getIntent().getBooleanExtra("userdeleted", true)) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // User is already logged in, redirect to the appropriate home page
                navigateToHomePage(currentUser);
            }
        } else {
            // If the user is not deleted, check if the user is already logged in
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

            } else if("Student".equals(userType))
            {
                startActivity(new Intent(getApplicationContext(), student_panel.class));
                finish();
            }
            else {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), student_login.class));
                overlay.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @SuppressLint("RestrictedApi")
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
                }else if(documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isStudent"))
                {
                    userType="Student";
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
    private void loginUser() {
        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextUsername.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }
        overlay.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        login.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String uid = user.getUid();
                            getUserType(uid, userType -> {
                                if ("Student".equals(userType)) {

                                    startActivity(new Intent(getApplicationContext(), teacher_panel.class));
                                    finish();
                                }
                                 else {
                                    Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    overlay.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Credential Invalid " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        overlay.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);

                    }
                });
    }
}
