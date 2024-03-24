package administratorpkg;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_classroom#newInstance} factory method to
 * create an instance of this fragment.
 */


public class fragment_classroom extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Subjects>subjectsArrayList;
    private ArrayList<String> subjectname;
    private ArrayList<String> teachername;
    private RecyclerView recyclerview;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private ArrayList<String> teacher;
    private SwipeRefreshLayout swipeRefreshLayout;
    Dialog myDialog ;




    FloatingActionButton openbtn;

    public fragment_classroom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassroomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_classroom newInstance(String param1, String param2) {
        fragment_classroom fragment = new fragment_classroom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_classroom, container, false);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        teacher = new ArrayList<>();
        dataInitialize();



        return rootView;
    }
    String documentId;
    String selectedTeacher;
    String selectedYear;
    Button submitbtn;
    String id;
    EditText classname;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        // Set an OnRefreshListener to handle the refresh action
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Perform the actions you want to do when the user triggers a refresh
                // For example, reload data from the server
                dataInitialize();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop the refreshing indicator
                        swipeRefreshLayout.setRefreshing(false);

                        // Hide the ProgressBar when refresh is complete

                    }
                }, 2000);
            }
        });



//        ClassrromViewAdapter classrromViewAdapter = new ClassrromViewAdapter(getContext(),subjectsArrayList, recyclerviewonclick);
//        recyclerview.setAdapter(classrromViewAdapter);


        openbtn = view.findViewById(R.id.addsubjectbtn);
        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closebtn;
                myDialog = new Dialog(requireContext());
                myDialog.setContentView(R.layout.add_subject_floating_panel);
                closebtn = myDialog.findViewById(R.id.closeaddsubjectpanel);
                submitbtn=myDialog.findViewById(R.id.submit_subject_info_btn);


                myDialog.setCanceledOnTouchOutside(false);

                Spinner spinnerTeacher = myDialog.findViewById(R.id.spinnerTeacher);
                Spinner spinnerYear = myDialog.findViewById(R.id.spinnerYear);
                CollectionReference collectionRef = fstore.collection("subjects");
                ArrayList<String> yearList = new ArrayList<>();

                // Fetching all documents from the collection
                collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Iterate through the documents
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Access the data of each document
                                String documentId = document.getId();
                                yearList.add(documentId);
                                Log.d("doc", "onComplete: " + documentId);
                            }
                            // Set up spinner adapter inside onComplete
                            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearList);
                            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerYear.setAdapter(yearAdapter);
                        } else {
                            // Handle errors
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                            Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                CollectionReference teachercollectionRef = fstore.collection("user");
                ArrayList<String> teacherList  = new ArrayList<>();

                // Fetching all documents from the collection
                teachercollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Iterate through the documents
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Access the data of each document
                                String documentId = document.getId();
                                DocumentReference df=fstore.collection("user").document(documentId);
                                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            // Access the 'name' field and do something with it
                                            String userName = documentSnapshot.getString("fullname");
                                            teacherList.add(userName);
                                            ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, teacherList);
                                            teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinnerTeacher.setAdapter(teacherAdapter);

                                        } else {
                                            Log.d("Firestore", "No such document");
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firestore", "Error getting user document", e);
                                    }
                                });


                                Log.d("doc", "onComplete: " + documentId);
                            }
                            // Set up spinner adapter inside onComplete

                        } else {
                            // Handle errors
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                            Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if(myDialog!=null){











                    // Listener for Teacher Names spinner
                    spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedTeacher = (String) parent.getItemAtPosition(position);
                            // Handle the selected teacher as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Do nothing here if needed
                        }
                    });

// Listener for Subject Names spinner



// Listener for Years spinner
                    spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedYear = (String) parent.getItemAtPosition(position);
                            // Handle the selected year as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Do nothing here if needed
                        }
                    });
                }


                submitbtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        fstore.collection("user")
                                .whereEqualTo("fullname", selectedTeacher)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                // Handle the document here
                                                documentId = documentSnapshot.getId();
                                                // Access other fields if needed

                                                // Log or use the retrieved data

                                            }
                                            DocumentReference teachersCollectionRef = fstore.collection("classteachers").document(documentId);

                                            // Create a new teacher document with fields
                                            Map<String, Object> teacherData = new HashMap<>();
                                            teacherData.put("fullname", selectedTeacher);
                                            teacherData.put("class year", selectedYear);


                                            // Add the document to the collection
                                            teachersCollectionRef.set(teacherData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), "classroom added", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });


                                        } else {
                                            // No document found with the given name
                                            Log.d(TAG, "No document found with name: " + selectedTeacher);
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error getting documents", e);
                                    }
                                });

                    }
                });



                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                myDialog.show();
            }
        });


    }


    private void dataInitialize() {
        teachername = new ArrayList<>();
        subjectname = new ArrayList<>();
        subjectsArrayList = new ArrayList<>();

        // Get user names
        CollectionReference userCollectionRef = fstore.collection("classteachers");
        userCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("fullname");

                        teachername.add(userName);
                        subjectname.add("classTeacher");
                    }

                    // After fetching user names, proceed to fetch class teacher names
                    initializeAdapter();
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                    Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






    private void initializeAdapter() {
        // Iterate through subject names and teacher names to create Subjects objects

        for (int j = 0; j < teachername.size(); j++) {
            Subjects subjects = new Subjects(subjectname.get(j), teachername.get(j));

            subjectsArrayList.add(subjects);
        }

        // Create and set adapter to RecyclerView
        ClassrromViewAdapter classrromViewAdapter = new ClassrromViewAdapter(getContext(), subjectsArrayList, new recyclerviewonclick() {

            @Override
            public void onItemClick(int position) {
                // Handle item click if needed
            }
        });
        classrromViewAdapter.notifyDataSetChanged();
        recyclerview.setAdapter(classrromViewAdapter);
        classrromViewAdapter.notifyDataSetChanged();
    }

}