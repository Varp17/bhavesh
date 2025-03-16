package teacherpkg;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginform.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class teacher_fragment_document_classroom extends Fragment {

    private static final int REQUEST_CODE_FILE_PICKER = 1;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private ImageView imageView;
    public TextView selectedfiletext;
    private Button uploadButton;
    private DatabaseReference databaseReference;
    private Uri fileUri;

    public teacher_fragment_document_classroom() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_teacher_fragment_document_classroom, container, false);

        imageView = view.findViewById(R.id.imageView);
        selectedfiletext = view.findViewById(R.id.selectFileText);
        uploadButton = view.findViewById(R.id.uploadbutton);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Documents");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == getActivity().RESULT_OK && data != null) {
            fileUri = data.getData();
            // Display the selected file's name
            String fileName = getFileName(fileUri);
            if (fileName != null) {
                // Update the selected file text
                selectedfiletext.setText(fileName);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        if (uri == null) return null;
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void uploadData() {
        if (fileUri != null) {
            String documentTitle = selectedfiletext.getText().toString().trim();
            if (!documentTitle.isEmpty()) {
                // Upload the file to Firebase Storage
                uploadFileToStorage(fileUri, documentTitle);
            } else {
                // Handle case where document title is empty
                Log.e(TAG, "Document title is empty");
            }
        } else {
            // Handle case where no file is selected
            Log.e(TAG, "No file selected");
        }
    }

    private void uploadFileToStorage(Uri fileUri, final String documentTitle) {
        // Get a reference to the location where you want to save the file in Firebase Storage
        StorageReference fileRef = storageRef.child("Documents").child(Objects.requireNonNull(fileUri.getLastPathSegment()));

        // Upload file to Firebase Storage
        UploadTask uploadTask = fileRef.putFile(fileUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // File uploaded successfully
            // You can get the download URL of the uploaded file if needed
            fileRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                String fileDownloadUrl = downloadUrl.toString();
                // Save the document title and file download URL to Firebase Realtime Database
                HashMap<String, Object> documentData = new HashMap<>();
                documentData.put("documentTitle", documentTitle);
                documentData.put("fileDownloadUrl", fileDownloadUrl);
                databaseReference.push().setValue(documentData)
                        .addOnSuccessListener(aVoid -> {
                            // Data uploaded successfully
                            Log.d(TAG, "Document uploaded to database");
                            Toast.makeText(getContext(), "Document uploaded to database", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Failed to upload data
                            Log.e(TAG, "Failed to upload document to database: " + e.getMessage());
                            Toast.makeText(getContext(), "Failed to upload document to database: ", Toast.LENGTH_SHORT).show();
                        });
            });
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.e(TAG, "Failed to upload file: " + exception.getMessage());
        });
    }
}