package administratorpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.loginform.R;

public class documentgridadapter extends BaseAdapter {

    Context context;
    String[] documentnames;
    LayoutInflater inflater;

    public documentgridadapter(fragment_documnet_in_classroom context, String[] documentnames) {
        this.context = context.getActivity(); // Use getActivity() to get the context from the Fragment
        this.documentnames = documentnames;
    }

    @Override
    public int getCount() {
        return documentnames.length; // Corrected the count
    }

    @Override
    public Object getItem(int position) {
        return documentnames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.documentview_cards, null);
        }

        TextView textView = convertView.findViewById(R.id.documentviewcard);
        textView.setText(documentnames[position]);

        return convertView;
    }
}
