package studentpakage;

import com.google.firebase.storage.StorageReference;

public class DataClass {
    private String imageURL;
    private String caption;

    public DataClass() {
        // Default constructor required for Firebase
    }

    public DataClass(String imageURL, String caption) {
        this.imageURL = imageURL;
        this.caption = caption;
    }

    // Constructor accepting a StorageReference
    public DataClass(StorageReference storageReference) {
        this.imageURL = storageReference.getPath(); // You may need to adjust this based on your StorageReference structure
        this.caption = ""; // Set a default caption or retrieve it from metadata if available
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
