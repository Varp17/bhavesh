package administratorpkg;

import static androidx.appcompat.app.AppCompatDelegate.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.loginform.R;


public class Addministrator_0r_Teacher_Login extends AppCompatActivity {

    TextView forgotPasswordLink;
    Button login;
    SpannableString content;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addministrator0r_teacher_login);
        forgotPasswordLink=findViewById(R.id.forgot);
        content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordLink.setText(content);
        login=findViewById(R.id.button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), administrotor_panel.class);
                startActivity(intent);
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