package administratorpkg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class feedback_activity_administrator extends varchi_line implements FeedbackAdapter.OnFeedbackClickListener {

    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;

    private FirebaseFirestore db;

    @Override
    public int getLayoutresId() {
        return R.layout.activity_feedback_administrator;
    }
    @NonNull
    public Fragment getItem(int position) {
        return null;
    }

    public int getCount() {
        return 0;
    }

    @Override
    String getactionbarTiile_in_varchi_line() {
        return "Feedback";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_administrator);

        recyclerView = findViewById(R.id.feedbackrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        loadFeedbackFromFirestore();
        // Load feedback data from Firestore
    }

    private void loadFeedbackFromFirestore() {
        db.collection("feedback")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Feedback> feedbackList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Feedback feedback = document.toObject(Feedback.class);
                            feedbackList.add(feedback);
                        }
                        displayFeedback(feedbackList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(feedback_activity_administrator.this, "Error loading feedback", Toast.LENGTH_SHORT).show();
                        Log.e("FeedbackActivity", "Error loading feedback", e);
                    }
                });
    }

    // Display feedback data in RecyclerView
    private void displayFeedback(List<Feedback> feedbackList) {
        feedbackAdapter = new FeedbackAdapter(feedbackList, this);
        recyclerView.setAdapter(feedbackAdapter);
    }

    @Override
    public void onFeedbackClick(Feedback feedback) {

    }

    @Override
    public void onFeedbackClick(int position) {
        if (feedbackAdapter != null) {
            Feedback feedback = feedbackAdapter.getItem(position);
            if (feedback != null) {
                Intent intent = new Intent(feedback_activity_administrator.this, FeedbackDetailActivity.class);
                intent.putExtra("subject", feedback.getSubject());
                intent.putExtra("description", feedback.getDescription());
                startActivity(intent);
            } else {
                Log.e("FeedbackActivity", "Feedback item is null at position: " + position);
                Toast.makeText(feedback_activity_administrator.this, "Feedback item is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("FeedbackActivity", "Feedback adapter is null");
            Toast.makeText(feedback_activity_administrator.this, "Feedback adapter is null", Toast.LENGTH_SHORT).show();
        }
    }
}

