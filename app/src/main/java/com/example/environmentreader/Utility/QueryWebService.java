package com.example.environmentreader.Utility;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class QueryWebService {

    Context mContext;

    public QueryWebService(Context mContext) {
        this.mContext = mContext;
    }

    public void getPSI(Response.Listener<String> responseListener) {
        // Request a string response from the provided URL.
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://api.data.gov.sg/v1/environment/psi";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,responseListener
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = error.toString();
            }
        });

        queue.add(stringRequest);
        // Add the request to the RequestQueue.
    }

    public void getPM25(Response.Listener<String> responseListener1) {
        // Request a string response from the provided URL.
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://api.data.gov.sg/v1/environment/psi";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,responseListener1
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = error.toString();
            }
        });

        queue.add(stringRequest);
        // Add the request to the RequestQueue.
    }
}