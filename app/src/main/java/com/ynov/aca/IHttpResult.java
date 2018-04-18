package com.ynov.aca;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IHttpResult {
    void httpSuccess(String requestType, String response);
    void httpSuccess(String requestType, JSONObject response);
    void httpError(String requestType, VolleyError error);
}
