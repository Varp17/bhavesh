package administratorpkg;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.loginform.R;

public class fragment_documnet_in_classroom extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public fragment_documnet_in_classroom() {
        // Required empty public constructor
    }

    public static fragment_documnet_in_classroom newInstance(String param1, String param2) {
        fragment_documnet_in_classroom fragment = new fragment_documnet_in_classroom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documnet_in_classroom, container, false);

        String[] documentnames = {"Java", "Python", "PHP", "C", "C++", "C", "SQL", "CSS", "JAVASCRIPT", "WEB_DL"};

        documentgridadapter gridAdapter = new documentgridadapter(fragment_documnet_in_classroom.this, documentnames);
        GridView gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "You Clicked On " + documentnames[position], Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
