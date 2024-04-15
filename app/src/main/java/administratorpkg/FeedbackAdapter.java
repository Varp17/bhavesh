package administratorpkg;

import android.content.Context;
import android.content.Intent;
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
    private OnFeedbackClickListener onFeedbackClickListener;
    private Context context;
    private static final int MAX_WORDS = 3; // Change this value as needed
    private static final int MAX_WORDS1 = 7; // Change this value as needed

    public FeedbackAdapter(List<Feedback> feedbackList, OnFeedbackClickListener onFeedbackClickListener) {
        this.feedbackList = feedbackList;
        this.onFeedbackClickListener = onFeedbackClickListener;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_card_design, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.bind(feedback);
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public Feedback getItem(int position) {
        if (position < 0 || position >= feedbackList.size()) {
            return null;
        }
        return feedbackList.get(position);
    }


    public class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subjectTextView;
        TextView descriptionTextView;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.feedback_subject);
            descriptionTextView = itemView.findViewById(R.id.description);
            context = itemView.getContext(); // Get context from itemView

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onFeedbackClickListener.onFeedbackClick(position);
            }
        }

        public void bind(Feedback feedback) {
            subjectTextView.setText(trimToWordCount(feedback.getSubject(), MAX_WORDS));
            descriptionTextView.setText(trimToWordCount(feedback.getDescription(), MAX_WORDS1));
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
    }

    public interface OnFeedbackClickListener {
//        void onFeedbackClick(int position);

        void onFeedbackClick(Feedback feedback);

        void onFeedbackClick(int position);
    }
}
