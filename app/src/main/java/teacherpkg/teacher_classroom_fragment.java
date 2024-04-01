package teacherpkg;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
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
import android.widget.Spinner;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacher_classroom_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class teacher_classroom_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Subjects>subjectsArrayList;
    private ArrayList<String> subjectname,teachername;

    private RecyclerView recyclerview;
    Dialog myDialog ;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    recyclerviewonclick recyclerviewonclick;


    FloatingActionButton openbtn;

    public teacher_classroom_fragment() {
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
    public static teacher_classroom_fragment newInstance(String param1, String param2) {
        teacher_classroom_fragment fragment = new teacher_classroom_fragment();
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
        View rootView = inflater.inflate(R.layout.activity_teacher_classroom_fragment, container, false);


        dataInitialize();


        return rootView;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkclassteacher();




        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

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

        recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), teacher_classroomclicked.class);


                intent.putExtra("name", subjectname.get(position));




                startActivity(intent);
            }

        };



        openbtn = view.findViewById(R.id.addsubjectbtn);

        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), mangesubject_classteacher.class));

            }
        });


    }
    String username;
    private void dataInitialize(){
        subjectsArrayList = new ArrayList<>();
        teachername=new ArrayList<>();
        subjectname=new ArrayList<>();


        DocumentReference documentReference=fstore.collection("user").document(fAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot= task.getResult();
                    username=documentSnapshot.getString("fullname");


                }
            }
        });

        addclassteacher();
        CollectionReference crf=fstore.collection("classroom_subject");
        crf.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {

                        String name = documentSnapshot.getString("fullname");


                                if(name.equals(username))
                                {
                                    teachername.add(documentSnapshot.getString("fullname"));
                                    subjectname.add(documentSnapshot.getId());

                                }

                    }
                    for(int i=0;i<subjectname.size();i++){
                        Subjects subjects = new Subjects((subjectname.get(i)), teachername.get(i));
                        subjectsArrayList.add(subjects);
                    }
                    intializeadapter();

                }else {
                    Toast.makeText(getContext(), "No Document Found", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void addclassteacher() {

        DocumentReference dr = fstore.collection("classteachers").document(fAuth.getCurrentUser().getUid());
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User is a class teacher

                        teachername.add(documentSnapshot.getString("fullname"));
                        subjectname.add("CLASS TEACHER'S");




                } else {
                    // User is not a class teacher
                    Toast.makeText(getContext(), "No Classteacher", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                intializeadapter();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error occurred, hide the button
                Log.d(TAG, "onFailure: failed classteaccher");
            }
        });
    }

    private void intializeadapter() {
        teacherviewadapter teacherViewAdapter = new teacherviewadapter(getContext(),subjectsArrayList, recyclerviewonclick);
        recyclerview.setAdapter(teacherViewAdapter);
        teacherViewAdapter.notifyDataSetChanged();

    }

    private void checkclassteacher() {
         fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();
        DocumentReference dr = fstore.collection("classteachers").document(id);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User is a class teacher
                    openbtn.setVisibility(View.VISIBLE);



                } else {
                    // User is not a class teacher
                    openbtn.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error occurred, hide the button
               openbtn.setVisibility(View.GONE);
            }
        });
    }


}