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

import administratorpkg.ClassrromViewAdapter;

public class teacherviewadapter extends RecyclerView.Adapter<teacherviewadapter.teacherViewHolder> implements recyclerviewonclick
{
    recyclerviewonclick recyclerviewonclick;
    Context context;
    ArrayList<Subjects> subjectsArrayList;
    public teacherviewadapter(Context context,ArrayList<Subjects> subjects , recyclerviewonclick recyclerviewonclick) {
        this.context = context;
        this.subjectsArrayList = subjects;
        this.recyclerviewonclick=recyclerviewonclick;

    }

    @NonNull
    @Override
    public teacherviewadapter.teacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.classroomview_cards,parent,false);
        return new teacherViewHolder(view,recyclerviewonclick);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherViewHolder holder, int position) {
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


    public static class teacherViewHolder extends RecyclerView.ViewHolder {

        TextView subjectname,teachername;

        public teacherViewHolder(@NonNull View itemView, recyclerviewonclick recyclerviewonclick) {
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


        public teacherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
