package studentpakage;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.ForgotPasswordActivity;
import com.example.loginform.R;

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
                startActivity(new Intent(student_login.this, student_panel.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(student_login.this, student_panel.class);
                startActivity(intent);
            }

        });
    }


}
