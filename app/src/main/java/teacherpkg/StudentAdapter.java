package teacherpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;

import administratorpkg.recyclerviewonclick;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> implements recyclerviewonclick {

    teacherpkg.recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Student> studentArrayList;



    public StudentAdapter(Context context, ArrayList<Student> student, teacherpkg.recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.studentArrayList = student;
        this.recyclerviewonclick = recyclerviewonclick;
    }


    @Override
    public void onItemClick(int position) {
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_view_student_manage, parent, false);
        return new StudentViewHolder(v,recyclerviewonclick);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {

        Student student = studentArrayList.get(position);
        holder.studentname.setText(student.studentname);
        // Store user ID in ViewHolder
        holder.userId = student.getStudentId();

    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder  {

        public String userId;
        TextView studentname;

        public StudentViewHolder(@NonNull View itemView, teacherpkg.recyclerviewonclick recyclerviewonclick) {
            super(itemView);
            studentname = itemView.findViewById(R.id.studentname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && StudentAdapter.this.recyclerviewonclick != null) {
                        StudentAdapter.this.recyclerviewonclick.onItemClick(position);
                    }
                }
            });
        }
    }
}
