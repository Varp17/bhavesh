package administratorpkg;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;
import java.util.List;

public class ClassrromViewAdapter extends RecyclerView.Adapter<ClassrromViewAdapter.ClassroomViewHolder> implements recyclerviewonclick
{
    recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Subjects> subjectsArrayList;
    public ClassrromViewAdapter(Context context,ArrayList<Subjects> subjects , recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.subjectsArrayList = subjects;
        this.recyclerviewonclick=recyclerviewonclick;

    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.classroomview_cards,parent,false);
        return new ClassroomViewHolder(v,recyclerviewonclick);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        Subjects sub = subjectsArrayList.get(position);
        holder.subjectname.setText(sub.subjectname);
        holder.teachername.setText(sub.teachername);
    }

    @Override
    public int getItemCount() {
        return subjectsArrayList.size();
    }


    @Override
    public void onItemClick(int position) {

    }


    public static class ClassroomViewHolder extends RecyclerView.ViewHolder {

        TextView subjectname,teachername;

        public ClassroomViewHolder(@NonNull View itemView,recyclerviewonclick recyclerviewonclick) {
            super(itemView);

            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerviewonclick!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){


                            recyclerviewonclick.onItemClick(pos);
                        }
                    }

                }
            });
        }



    }
}
