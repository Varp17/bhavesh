package administratorpkg;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepository {

    private FirebaseFirestore db;

    public DocumentRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllDocumentsFromFolder(String folderPath, final OnDocumentFetchListener listener) {
        db.collection("Documents")
                .whereEqualTo("folderPath", folderPath)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Document> documents = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Document document = documentSnapshot.toObject(Document.class);
                        documents.add(document);
                    }
                    listener.onDocumentsFetched(documents);
                })
                .addOnFailureListener(e -> listener.onFetchError(e));
    }

    public interface OnDocumentFetchListener {
        void onDocumentsFetched(List<Document> documents);

        void onFetchError(Exception e);
    }
}
