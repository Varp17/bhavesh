

package teacherpkg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.loginform.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teacherpkg.teacher_fragment_attendance_classroom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teacher_fragment_attendance_classroom extends Fragment {
    ImageButton imageButton;
    ImageButton imageButton1;
    Button take_attendance_btn ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teacher_fragment_attendance_classroom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_attendance_in_classroom.
     */
    // TODO: Rename and change types and number of parameters
    public static administratorpkg.fragment_attendance_in_classroom newInstance(String param1, String param2) {
        administratorpkg.fragment_attendance_in_classroom fragment = new administratorpkg.fragment_attendance_in_classroom();
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

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_teacher_fragment_attendance_classroom, container, false);
        take_attendance_btn = rootView.findViewById(R.id.takeattendancebtn);

        take_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Take_Attendance_Activity.class));
            }
        });
        return rootView;

    }


}