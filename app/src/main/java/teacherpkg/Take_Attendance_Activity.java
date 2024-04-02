package teacherpkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.loginform.R;

import java.util.Date;

import administratorpkg.dategiver;

public class Take_Attendance_Activity extends varchi_line {

    TextView classname;
    TextView datetextview;
    String subname;

    @Override
    int getLayoutresId() {
        return R.layout.activity_take_attendance;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "Attendance";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.teacher_color));

        datetextview = findViewById(R.id.date);

        SharedPreferences sharedPreferences = getSharedPreferences("subname", Context.MODE_PRIVATE);
        subname = sharedPreferences.getString("subname", null);


        if (datetextview != null) {
            datetextview.setText("Date: " + dategiver.getdate());
        }
        Toast.makeText(this, subname, Toast.LENGTH_SHORT).show();
    }

    public void attendancebackbtn(View view) {
        finish();
    }
}
