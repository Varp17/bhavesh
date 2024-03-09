package administratorpkg;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.R;

public class all_college_notiFication_from_Administrator extends varchi_line {

    private Dialog myDialog;
    Button notifycollegebtn;

    @Override
    int getLayoutresId() {
        return R.layout.activity_all_college_notification_from_administrator;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "NOTIFICATIONS";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assuming you have a Button with ID notifycollegebtn in your layout file
        notifycollegebtn = findViewById(R.id.notifycollegebtn);
        notifycollegebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDialog();
            }
        });
    }

    private void showNotificationDialog() {
        // Assuming you have a layout file named administrator_notification_edit_panel
        myDialog = new Dialog(all_college_notiFication_from_Administrator.this);
        myDialog.setContentView(R.layout.administrator_notification_edit_panel);

        TextView notification_close_btn = myDialog.findViewById(R.id.notification_close_btn);
        notification_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }
}
