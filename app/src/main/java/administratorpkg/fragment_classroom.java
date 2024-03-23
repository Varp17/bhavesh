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
    private String[] subjectname;
    private String[] teachername;
    private RecyclerView recyclerview;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
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
        dataInitialize();
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        return rootView;
    }

    String selectedTeacher;
    String selectedYear;
    Button submitbtn;
    String id;
    EditText classname;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();



        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        recyclerviewonclick recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), classroomClicked_activity.class);


                    intent.putExtra("name",subjectname[position]);




                startActivity(intent);
            }

        };

        ClassrromViewAdapter classrromViewAdapter = new ClassrromViewAdapter(getContext(),subjectsArrayList, recyclerviewonclick);
        recyclerview.setAdapter(classrromViewAdapter);
        classrromViewAdapter.notifyDataSetChanged();

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



                        CollectionReference userCollection = fstore.collection("user");

// Query for documents where fullname equals "swapnil"
                        Query query = userCollection.whereEqualTo("fullname", selectedTeacher);

// Execute the query
                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    // Access the document data
                                    String fullname = documentSnapshot.getString("fullname");
                                    id=documentSnapshot.getId();
                                }
                                // Create a HashMap to hold your data
                                Map<String, Object> teacherInfo = new HashMap<>();
                                teacherInfo.put("classteacher_name", selectedTeacher);
                                teacherInfo.put("classteacher_id", id);
                                // Add more key-value pairs as needed

                                // Get the Firestore reference
                                DocumentReference dfr = fstore.collection("classteacher_" + selectedTeacher).document("classTeacherInfo");

                                // Set the data to the document
                                dfr.set(teacherInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Document successfully written
                                                Log.d("er", "DocumentSnapshot successfully written!");
                                                // You can put any success logic here
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle any errors
                                                Log.w("er", "Error writing document", e);
                                                // You can put any failure logic here
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle any errors
                                Log.e(TAG, "Error getting documents: ", e);
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
    private void dataInitialize(){
        subjectsArrayList = new ArrayList<>();
        subjectname = new String[]{
                "CLASS TEACHER'S",
                getString(R.string.sub1),
                getString(R.string.sub2),
                getString(R.string.sub3),
                getString(R.string.sub4),

        };
        teachername = new String[]{
                "Swapnil Sir",
                getString(R.string.tech1),
                getString(R.string.tech2),
                getString(R.string.tech3),
                getString(R.string.tech4)
        };
        for(int i=0;i<subjectname.length;i++){
            Subjects subjects = new Subjects((subjectname[i]),teachername[i]);
            subjectsArrayList.add(subjects);
        }

    }

}