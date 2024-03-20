package administratorpkg;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            getUserType(uid, userType -> {
                                if ("Teacher".equals(userType)) {
                                    startActivity(new Intent(getApplicationContext(), teacher_panel.class));
                                    finish();
                                } else if ("Admin".equals(userType)) {
                                    startActivity(new Intent(getApplicationContext(), administrotor_panel.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Credential Invalid " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    String usertype ;



    public void getUserType(String uid, UserTypeCallback callback) {
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
}
