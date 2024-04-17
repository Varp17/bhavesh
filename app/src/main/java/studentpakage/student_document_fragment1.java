package studentpakage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class student_document_fragment1 extends Fragment {

    private RecyclerView recyclerView;
    private documentadapter adapter;
    private ArrayList<DataClass> documentReferences = new ArrayList<>();
    private StorageReference storageReference;

    public student_document_fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference().child("Documents");
        fetchDocumentReferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_document_fragment1, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new documentadapter(documentReferences, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fetchDocumentReferences() {
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                // Assuming DataClass has a constructor that takes a StorageReference
                DataClass data = new DataClass(item);
                documentReferences.add(data);
            }
            adapter.notifyDataSetChanged(); // Notify adapter after adding all items
        }).addOnFailureListener(exception -> {
            // Handle failure to list items
        });
    }
}
