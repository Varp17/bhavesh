package teacherpkg;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.loginform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        dataInitialize();



        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        recyclerviewonclick recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), teacher_classroomclicked.class);


                intent.putExtra("name", subjectname.get(position));




                startActivity(intent);
            }

        };

        teacherviewadapter teacherViewAdapter = new teacherviewadapter(getContext(),subjectsArrayList, recyclerviewonclick);
        recyclerview.setAdapter(teacherViewAdapter);
        teacherViewAdapter.notifyDataSetChanged();

        openbtn = view.findViewById(R.id.addsubjectbtn);

        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), mangesubject_classteacher.class));

            }
        });


    }
    private void dataInitialize(){
        subjectsArrayList = new ArrayList<>();
        teachername=new ArrayList<>();
        subjectname=new ArrayList<>();
        teachername.add("Suyog Teacher");
        subjectname.add("CLASS TEACHRE'S");
        teachername.add("Suyog Teacher");
        subjectname.add("EDI");
        teachername.add("Suyog Teacher");
        subjectname.add("BEC");
        teachername.add("Suyog Teacher");
        subjectname.add("PCI");


        for(int i=0;i<subjectname.size();i++){
            Subjects subjects = new Subjects((subjectname.get(i)), teachername.get(i));
            subjectsArrayList.add(subjects);
        }

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