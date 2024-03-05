package administratorpkg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loginform.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    Dialog myDialog;

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


        return rootView;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();

        recyclerview = view.findViewById(R.id.classroomrecyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        ClassrromViewAdapter classrromViewAdapter = new ClassrromViewAdapter(getContext(),subjectsArrayList);
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
                getString(R.string.sub1),
                getString(R.string.sub2),
                getString(R.string.sub3),
                getString(R.string.sub4),

        };
        teachername = new String[]{
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