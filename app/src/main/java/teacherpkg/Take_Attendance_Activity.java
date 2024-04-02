package teacherpkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginform.R;

public class Take_Attendance_Activity extends AppCompatActivity {



    String subjectclass;
    String subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.teacher_color));
        setContentView(R.layout.activity_take_attendance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      SharedPreferences sharedPreferences = getSharedPreferences("subname", Context.MODE_PRIVATE);

         subname = sharedPreferences.getString("subname", null);

        Toast.makeText(this, subname, Toast.LENGTH_SHORT).show();

    }

    public void attendancebackbtn(View view) {
        finish();
    }
}