package administratorpkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

public class TeacherForgotPasswordActivity extends AppCompatActivity {


    private EditText regedittext;
    private Button sub_btn;
    private TextView notice;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_forgot_password);
        regedittext=findViewById(R.id.registerdemail);
        sub_btn=findViewById(R.id.submit);
        notice=findViewById(R.id.notice);
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FirebaseAuth mAuth = FirebaseAuth.getInstance();

                mAuth.sendPasswordResetEmail(regedittext.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(getApplicationContext(), "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                    notice.setText("email had sent you can reset password from email  ");
                                } else {
                                    // Password reset email sending failed
                                    Toast.makeText(getApplicationContext(), "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}