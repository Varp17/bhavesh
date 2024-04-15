package administratorpkg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbackList;
    private static final int MAX_WORDS = 3; // Change this value as needed
    private static final int MAX_WORDS1 = 7; // Change this value as needed

    private OnFeedbackClickListener onFeedbackClickListener;

    public FeedbackAdapter(List<Feedback> feedbackList, OnFeedbackClickListener onFeedbackClickListener) {
        this.feedbackList = feedbackList;
        this.onFeedbackClickListener = onFeedbackClickListener;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_card_design, parent, false);
        return new FeedbackViewHolder(view, onFeedbackClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.subjectTextView.setText(trimToWordCount(feedback.getSubject(), MAX_WORDS));
        holder.descriptionTextView.setText(trimToWordCount(feedback.getDescription(), MAX_WORDS1));

    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    private String trimToWordCount(String text, int wordCount) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String[] words = text.split("\\s+");
        StringBuilder trimmedText = new StringBuilder();

        for (int i = 0; i < Math.min(wordCount, words.length); i++) {
            trimmedText.append(words[i]).append(" ");
        }

        return trimmedText.toString().trim();
    }
    public Feedback getItem(int position) {
        return feedbackList.get(position);
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subjectTextView;
        TextView descriptionTextView;
        OnFeedbackClickListener onFeedbackClickListener;

        public FeedbackViewHolder(@NonNull View itemView, OnFeedbackClickListener onFeedbackClickListener) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.feedback_subject);
            descriptionTextView = itemView.findViewById(R.id.description);
            this.onFeedbackClickListener = onFeedbackClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFeedbackClickListener.onFeedbackClick(getAdapterPosition());
        }
    }

    public interface OnFeedbackClickListener {
        void onFeedbackClick(int position);
    }
}
