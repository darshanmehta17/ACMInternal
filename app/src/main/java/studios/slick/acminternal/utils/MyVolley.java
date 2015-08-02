package studios.slick.acminternal.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Darshan on 11-02-2015.
 */
public class MyVolley {
    JsonObjectRequest jsonRequest;
    OnSuccessListener onSuccessListener;
    OnFailureListener onFailureListener;

    public void insertRequest(Context context,String URL,JSONObject jsonObject){

        jsonRequest= new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
               onSuccessListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                onFailureListener.onFail(error);
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonRequest);
    }



    public interface OnSuccessListener {
        void onSuccess(JSONObject response);
    }

    public interface OnFailureListener {
        void onFail(VolleyError error);
    }

    public void setOnSuccessListener(final OnSuccessListener onSuccessListener) {
        this.onSuccessListener=onSuccessListener;
    }

    public void setOnFailureListener(final OnFailureListener onFailureListener){
        this.onFailureListener=onFailureListener;
    }
}
