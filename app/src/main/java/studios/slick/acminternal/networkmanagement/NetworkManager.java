package studios.slick.acminternal.networkmanagement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Darshan on 10/06/15.
 */
public class NetworkManager {

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ( (networkInfo != null) && (networkInfo.isConnected()) );
    }

}
