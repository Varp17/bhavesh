package administratorpkg;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginform.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link staffNotification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class staffNotification extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<notification_argument> subjectsArrayList1;
    private String[] subjectname;
    private String[] teachername;
    private RecyclerView recyclerview;
    Dialog myDialog;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment staffNotification.
     */
    // TODO: Rename and change types and number of parameters
    public static staffNotification newInstance(String param1, String param2) {
        staffNotification fragment = new staffNotification();
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
        // return inflater.inflate(R.layout.fragment_attendance_in_classroom, container, false);
        View rootView = inflater.inflate(R.layout.fragment_staff_notification, container, false);
        dataInitialize();


        return rootView;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();

        recyclerview = view.findViewById(R.id.notificationrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        notificationviewadapter classrromViewAdapter = new notificationviewadapter(getContext(),subjectsArrayList1);
        recyclerview.setAdapter(classrromViewAdapter);
        classrromViewAdapter.notifyDataSetChanged();


    }
    private void dataInitialize(){
        subjectsArrayList1 = new ArrayList<>();
        subjectname = new String[]{
                getString(R.string.noti1),
                getString(R.string.noti2),
                getString(R.string.noti3),
                getString(R.string.noti4),
                getString(R.string.noti5),
                getString(R.string.noti5),
                getString(R.string.noti3),
                getString(R.string.noti1),
                getString(R.string.noti2),
                getString(R.string.noti2),
                getString(R.string.noti5),

        };
        teachername = new String[]{
                getString(R.string.desp1),
                getString(R.string.desp2),
                getString(R.string.desp3),
                getString(R.string.desp4),
                getString(R.string.desp5),
                getString(R.string.desp4),
                getString(R.string.desp2),
                getString(R.string.desp1),
                getString(R.string.desp3),
                getString(R.string.desp5),
                getString(R.string.desp1),
        };
        for(int i=0;i<subjectname.length;i++){
            notification_argument subjects = new notification_argument((subjectname[i]),teachername[i]);
            subjectsArrayList1.add(subjects);
        }

    }

}
