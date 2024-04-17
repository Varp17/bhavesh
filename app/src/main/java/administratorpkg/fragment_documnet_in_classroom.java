package administratorpkg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_documnet_in_classroom extends Fragment {

    private RecyclerView recyclerView;
    private DocumentGridAdapter adapter;
    private DocumentRepository documentRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_documnet_in_classroom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewDocuments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        documentRepository = new DocumentRepository();
        adapter = new DocumentGridAdapter(getContext(), new ArrayList<>());

        recyclerView.setAdapter(adapter);

        // Retrieve documents from Firestore
        retrieveDocumentsFromFolder("/Documents");
    }

    private void retrieveDocumentsFromFolder(String folderPath) {
        documentRepository.getAllDocumentsFromFolder(folderPath, new DocumentRepository.OnDocumentFetchListener() {
            @Override
            public void onDocumentsFetched(List<Document> documents) {
                adapter.updateDocuments(documents);
            }

            @Override
            public void onFetchError(Exception e) {
                // Handle fetch error
            }
        });
    }
}
