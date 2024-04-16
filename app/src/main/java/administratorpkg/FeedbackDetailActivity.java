package administratorpkg;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.R;

public class FeedbackDetailActivity extends AppCompatActivity {

    private TextView textViewSubject;
    private TextView textViewDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // Initialize TextViews
        textViewSubject = findViewById(R.id.textViewSubjectValue);
        textViewDescription = findViewById(R.id.textViewDescriptionValue);

        // Get the feedback details passed from the previous activity
        String subject = getIntent().getStringExtra("subject");
        String description = getIntent().getStringExtra("description");

        // Set the feedback details to the TextViews
        textViewSubject.setText(subject);
        textViewDescription.setText(description);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide(); // Hide the action bar
        }

        // Implement logic to close the dialog (for example, when the user clicks a close button)
        // You can implement this logic as per your UI design
//        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(Activity.RESULT_OK);
//                finish(); // Close the dialog
//            }
//        });
    }
}
