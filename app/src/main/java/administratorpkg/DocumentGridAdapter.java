package administratorpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginform.R;

import java.util.List;

public class DocumentGridAdapter extends RecyclerView.Adapter<DocumentGridAdapter.ViewHolder> {

    private Context context;
    private List<Document> documentList;

    public DocumentGridAdapter(Context context, List<Document> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_document, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Document document = documentList.get(position);
        holder.textViewDocumentName.setText(document.getName());
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void updateDocuments(List<Document> documents) {
        documentList.clear(); // Clear the existing list
        documentList.addAll(documents); // Add new documents
        notifyDataSetChanged(); // Notify adapter of the data change
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDocumentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDocumentName = itemView.findViewById(R.id.textViewDocumentName);
        }
    }
}
