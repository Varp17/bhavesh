package administratorpkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import teacherpkg.teacher_panel;

public class Addministrator_0r_Teacher_Login extends AppCompatActivity {

    TextView forgotPasswordLink;
    Button login;
    private EditText editTextUsername, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addministrator0r_teacher_login);

        forgotPasswordLink = findViewById(R.id.forgot);
        login = findViewById(R.id.button);
        editTextUsername = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.word);

        mAuth = FirebaseAuth.getInstance();

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
                            if (user.getEmail().contains("teacher")) {
                                startActivity(new Intent(getApplicationContext(), teacher_panel.class));
                            } else if (user.getEmail().contains("admin")) {
                                startActivity(new Intent(getApplicationContext(), administrotor_panel.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Unknown user type", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
