package studentpakage;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loginform.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_attendance_fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_attendance_fragment1 extends Fragment {
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList list;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public student_attendance_fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_attendance_fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static student_attendance_fragment1 newInstance(String param1, String param2) {
        student_attendance_fragment1 fragment = new student_attendance_fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        barChart.findViewById(R.id.barchart);
//        getdata();
//        barDataSet=new BarDataSet(list,"Dataset");
//        barData=new BarData(barDataSet);
//        barChart.setData(barData);
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(17f);
//        barChart.getDescription().setEnabled(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_attendance_fragment1, container, false);
    }
    private void getdata()
    {
        list = new ArrayList<>();
        list.add(new BarEntry(1f,2));
        list.add(new BarEntry(2f,2));
        list.add(new BarEntry(3f,7));
        list.add(new BarEntry(4f,2));
        list.add(new BarEntry(5f,4));
        list.add(new BarEntry(6f,9));

    }
}