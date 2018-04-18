package com.ynov.aca.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ynov.aca.IHttpResult;

import org.json.JSONObject;

public class HttpService {

    IHttpResult mResultCallback = null;
    Context mContext;

    public HttpService(IHttpResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void postData(String url, JSONObject sendObj) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url, sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.httpSuccess("POST", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.httpError("POST",error);
                }
            });

            queue.add(jsonObj);

        } catch(Exception e) {
            Log.e("HttpError", e.getMessage());
        }
    }

    public void getData(String url)
    {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(mResultCallback != null) {
                            mResultCallback.httpSuccess("GET", response);
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null)
                            mResultCallback.httpError("GET", error);
                    }
                }
            );

            queue.add(getRequest);

        } catch(Exception e) {
            Log.e("HttpError", e.getMessage());
        }
    }

}