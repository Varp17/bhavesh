package teacherpkg;

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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.loginform.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.Subject;

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
    private String[] subjectname;
    private String[] teachername;
    private RecyclerView recyclerview;
    Dialog myDialog ;




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
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);
        dataInitialize();


        return rootView;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();



        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        recyclerviewonclick recyclerviewonclick=new recyclerviewonclick() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), teacher_classroomclicked.class);


                intent.putExtra("name",subjectname[position]);




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
                TextView closebtn;
                myDialog = new Dialog(requireContext());
                myDialog.setContentView(R.layout.add_subject_floating_panel);
                closebtn = myDialog.findViewById(R.id.closeaddsubjectpanel);

                myDialog.setCanceledOnTouchOutside(false);

                Spinner spinnerTeacher = myDialog.findViewById(R.id.spinnerTeacher);
                Spinner spinnerYear = myDialog.findViewById(R.id.spinnerYear);

                if(myDialog!=null){

                    List<String> subjectList = Arrays.asList("MAD", "PHP", "Python", "ETI");
                    List<String> teacherList = Arrays.asList("Prerana Mam", "Vaishnavi Mam", "Trupti Mam", "Shirin Mam");
                    List<String> yearList = Arrays.asList("FY","SY","TY");

                    ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, teacherList);
                    teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTeacher.setAdapter(teacherAdapter);

                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, subjectList);
                    subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearList);
                    yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerYear.setAdapter(yearAdapter);

                    // Listener for Teacher Names spinner
                    spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedTeacher = (String) parent.getItemAtPosition(position);
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
                            String selectedYear = (String) parent.getItemAtPosition(position);
                            // Handle the selected year as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Do nothing here if needed
                        }
                    });
                }

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