package administratorpkg;

import static androidx.appcompat.app.AppCompatDelegate.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import com.example.loginform.R;

import teacherpkg.teacher_panel;


public class Addministrator_0r_Teacher_Login extends AppCompatActivity {

    TextView forgotPasswordLink;
    Button login;
    private EditText editTextUsername, editTextPassword;
    SpannableString content;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addministrator0r_teacher_login);
        forgotPasswordLink=findViewById(R.id.forgot);
        content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordLink.setText(content);
        login=findViewById(R.id.button);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.word);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get the entered username and password
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Replace this with your authentication logic
                if (username.equals("1") && password.equals("p")) {
                    // Successful login
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    // Proceed to teacher interface
                    Intent intent = new Intent(getApplicationContext(), teacher_panel.class);
                    startActivity(intent);
                }
                if (username.equals("1") && password.equals("a")){
                    Intent intent=new Intent(getApplicationContext(), administrotor_panel.class);
                    startActivity(intent);
                }
                else {
                    // Invalid credentials
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addministrator_0r_Teacher_Login.this, TeacherForgotPasswordActivity.class));
            }
        });
    }

}