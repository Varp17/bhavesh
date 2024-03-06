package administratorpkg;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.ArrayList;
import java.util.List;

public class notificationviewadapter extends RecyclerView.Adapter<notificationviewadapter.ClassroomViewHolder>
{
    Context context;
    ArrayList<notification_argument> subjectsArrayList1;
    public notificationviewadapter(Context context,ArrayList<notification_argument> subjects) {
        this.context = context;
        this.subjectsArrayList1 = subjects;

    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notificationview_cards,parent,false);
        return new ClassroomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        notification_argument sub = subjectsArrayList1.get(position);
        holder.subjectname.setText(sub.subjectname);
        holder.teachername.setText(sub.teachername);
    }

    @Override
    public int getItemCount() {
        return subjectsArrayList1.size();
    }

    public static class ClassroomViewHolder extends RecyclerView.ViewHolder{

        TextView subjectname,teachername;

        public ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectname = itemView.findViewById(R.id.subjectname);
            teachername = itemView.findViewById(R.id.teachername);
        }
    }
}
