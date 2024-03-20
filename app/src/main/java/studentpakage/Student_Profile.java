package studentpakage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginform.R;

public class Student_Profile extends AppCompatActivity {
TextView student_name,studenterno,studentbranch,studentdob,student_address,studentmobo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_profile2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        student_name=findViewById(R.id.student_profilename);
        studenterno =findViewById(R.id.student_enrollmet_no);
        studentbranch = findViewById(R.id.student_profilebranch);
        studentdob =findViewById(R.id.student_profile_dob);
        student_address = findViewById(R.id.profile_student_address);
        studentmobo =findViewById(R.id.student_mobileno);
        TextView studentInfo[]= {student_name,studenterno,studentbranch,studentdob,student_address,studentmobo};
        for (TextView i:studentInfo
             ) {


        }
    }
}