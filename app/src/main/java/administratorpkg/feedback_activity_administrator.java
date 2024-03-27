package administratorpkg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;
import java.util.List;

public class feedback_activity_administrator extends varchi_line implements FeedbackAdapter.OnFeedbackClickListener {

    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;

    @Override
    public int getLayoutresId() {
        return R.layout.activity_feedback_administrator;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "Feedback";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recyclerView = findViewById(R.id.feedbackrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mock data - replace this with your actual data retrieval logic
        List<Feedback> feedbackList = generateMockFeedbacks();

        feedbackAdapter = new FeedbackAdapter(feedbackList, this);
        recyclerView.setAdapter(feedbackAdapter);
    }

    // Mock data for testing, replace with your actual data retrieval logic
    private List<Feedback> generateMockFeedbacks() {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(new Feedback("Subject 1", "Description 1"));
        feedbackList.add(new Feedback("Subject 2", "Description 2"));
        feedbackList.add(new Feedback("Subject 3", "Description 3"));
        // Add more feedback items as needed
        return feedbackList;
    }

    @Override
    public void onFeedbackClick(int position) {
        Feedback feedback = feedbackAdapter.getItem(position);

        // Example: Display feedback details in a Toast, you can open a new activity or fragment instead
        String details = "Subject: " + feedback.getSubject() + "\n" +
                "Description: " + feedback.getDescription() + "\n";

        Toast.makeText(this, details, Toast.LENGTH_SHORT).show();
    }
}
