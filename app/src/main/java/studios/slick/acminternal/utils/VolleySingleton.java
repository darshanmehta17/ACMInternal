package studios.slick.acminternal.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

	private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }
 
    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }
    
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    
    public void addToRequestQueue(JsonObjectRequest req) {
        getRequestQueue().add(req);
    }
        
}
