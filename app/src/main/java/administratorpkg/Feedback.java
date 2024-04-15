package administratorpkg;

public class Feedback {
    private String subject;
    private String description;

    // Default constructor
    public Feedback() {
        // Required empty constructor for Firestore deserialization
    }

    public Feedback(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    // Getters and setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
