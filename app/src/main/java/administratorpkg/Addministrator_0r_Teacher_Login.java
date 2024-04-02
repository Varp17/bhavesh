package administratorpkg;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.example.loginform.student_login;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import teacherpkg.teacher_panel;

public class Addministrator_0r_Teacher_Login extends AppCompatActivity {

    TextView forgotPasswordLink;
    Button login;
    private EditText editTextUsername, editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;
    private FrameLayout overlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addministrator0r_teacher_login);

        forgotPasswordLink = findViewById(R.id.forgot);
        login = findViewById(R.id.button);
        editTextUsername = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.word);

        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        overlay = findViewById(R.id.overlay_teacher);
        progressBar = findViewById(R.id.progressBar_teacher);
//        Sprite wave = new Wave();
//        progressBar.setIndeterminateDrawable(wave);

        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

//        Sprite x = new ThreeBounce();
//        progressBar.setIndeterminateDrawable(x);

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addministrator_0r_Teacher_Login.this, TeacherForgotPasswordActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });
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
                                if ("Teacher".equals(userType)) {
                                    saveCredentials(email, password);
                                    startActivity(new Intent(getApplicationContext(), teacher_panel.class));
                                    finish();
                                } else if ("Admin".equals(userType)) {
                                    saveCredentials(email, password);
                                    startActivity(new Intent(getApplicationContext(), administrotor_panel.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(), student_login.class);
                                    intent.putExtra("userdeleted",false);
                                    startActivity(intent);
                                    finish();
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
    String usertype ;



    public void getUserType(String uid, UserTypeCallback callback) {
            Log.d(TAG, "getUserType method called");


        DocumentReference df = fstore.collection("user").document(uid);
        df.get().addOnSuccessListener(documentSnapshot -> {
            String userType = "Unknown";
            if (documentSnapshot.exists()) {
                if (documentSnapshot.getBoolean("isTeacher") != null && documentSnapshot.getBoolean("isTeacher")==true) {
                    userType = "Teacher";
                } else if (documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isAdmin")==true) {
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
    interface UserTypeCallback {
        void onUserTypeReceived(String userType);
    }

   
//    protected void onStart() {
//        super.onStart();
//        DocumentReference df = fstore.collection("user").document(mAuth.getCurrentUser().getUid());
//        df.get().addOnSuccessListener(documentSnapshot -> {
//            String userType = "Unknown";
//            if (documentSnapshot.exists()) {
//                if (documentSnapshot.getBoolean("isTeacher") != null && documentSnapshot.getBoolean("isTeacher") == true) {
//                    Intent intent = new Intent(getApplicationContext(), teacher_panel.class);
//                } else if (documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isAdmin") == true) {
//                    userType = "Admin";
//                }
//            } else {
//                Log.d(TAG, "Document does not exist");
//            }
//        });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart method called");
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            navigateToHomePage(currentUser);
//        }
//    }


//    private void navigateToHomePage(@NonNull FirebaseUser user) {
//        Log.d(TAG, "navigateToHomePage method called");
//        String uid = user.getUid();
//        getUserType(uid, userType -> {
//            if ("Teacher".equals(userType)) {
//                startActivity(new Intent(getApplicationContext(), teacher_panel.class));
//                finish();
//            } else if ("Admin".equals(userType)) {
//                startActivity(new Intent(getApplicationContext(), administrotor_panel.class));
//                finish();
//            } else {
//                Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_SHORT).show();
//            }
//            finish();
//        });
//    }

    private void saveCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("CRED", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

}
