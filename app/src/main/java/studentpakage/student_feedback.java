package studentpakage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class student_feedback extends varchi_line implements FeedbackAdapter.OnFeedbackClickListener {

    private EditText subjectEditText;
    private EditText descriptionEditText;
    private Button submitButton;
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private FirebaseFirestore db;

    @Override
    public int getLayoutresId() {
        return R.layout.activity_student_feedback;
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
        return "Feedback";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        subjectEditText = findViewById(R.id.subject_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        submitButton = findViewById(R.id.submit_feedback_button);
        recyclerView = findViewById(R.id.feedbackrecyclerview);

     //   recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mock data - replace this with your actual data retrieval logic
        //List<Feedback> feedbackList = generateMockFeedbacks();

//        feedbackAdapter = new FeedbackAdapter(feedbackList, this);
//        recyclerView.setAdapter(feedbackAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (!subject.isEmpty() && !description.isEmpty()) {
                    saveFeedbackToFirestore(subject, description); // Save feedback to Firestore
                } else {
                    Toast.makeText(student_feedback.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Mock data for testing, replace with your actual data retrieval logic
//    private List<Feedback> generateMockFeedbacks() {
//        List<Feedback> feedbackList = new ArrayList<>();
//        feedbackList.add(new Feedback("Subject 1", "Description 1"));
//        feedbackList.add(new Feedback("Subject 2", "Description 2"));
//        feedbackList.add(new Feedback("Subject 3", "Description 3"));
//        // Add more feedback items as needed
//        return feedbackList;
//    }


    private void saveFeedbackToFirestore(String subject, String description) {
        // Create a new Feedback object
        Feedback feedback = new Feedback(subject, description);

        // Add a new document with a generated ID
        db.collection("feedback")
                .add(feedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(student_feedback.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(student_feedback.this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        subjectEditText.setText("");
        descriptionEditText.setText("");
    }

    @Override
    public void onFeedbackClick(int position) {

    }

//    public void onFeedbackClick(int position) {
//        Feedback feedback = feedbackAdapter.getItem(position);
//
//        // Example: Display feedback details in a Toast, you can open a new activity or fragment instead
//        String details = "Subject: " + feedback.getSubject() + "\n" +
//                "Description: " + feedback.getDescription() + "\n";
//
//        Toast.makeText(this, details, Toast.LENGTH_SHORT).show();
//    }
}
