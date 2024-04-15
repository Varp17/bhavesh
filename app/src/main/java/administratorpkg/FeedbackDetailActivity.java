package administratorpkg;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginform.R;

public class FeedbackDetailActivity extends AppCompatActivity {

    private TextView textViewSubject;
    private TextView textViewDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);

        // Initialize TextViews
        textViewSubject = findViewById(R.id.textViewSubjectValue);
        textViewDescription = findViewById(R.id.textViewDescriptionValue);

        // Get the feedback details passed from the previous activity
        String subject = getIntent().getStringExtra("subject");
        String description = getIntent().getStringExtra("description");

        // Set the feedback details to the TextViews
        textViewSubject.setText(subject);
        textViewDescription.setText(description);
    }
}
