package administratorpkg;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class connectionDetector {
    private Context context;

    public connectionDetector(Context context) {
        this.context = context;
    }

    public  boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
