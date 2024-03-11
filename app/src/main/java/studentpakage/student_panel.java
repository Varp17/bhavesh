package studentpakage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.R;

public class student_panel extends AppCompatActivity {

    Button login;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

//        login=findViewById(R.id.button);
//
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(activity_student_panel.this, activity_student_panel.class);
//                startActivity(intent);
//            }
//        });
    }


}