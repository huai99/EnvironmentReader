package com.example.environmentreader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.environmentreader.Data.PM25Data;
import com.example.environmentreader.Data.PSIData;
import com.example.environmentreader.Utility.DataService;
import com.example.environmentreader.Utility.QueryWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PSI extends AppCompatActivity {

    TextView southvalue, northvalue, eastvalue, westvalue, centralvalue, nationalvalue;
    QueryWebService webService;
    DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psi);
        Intent intent = getIntent();
        getSupportActionBar().setTitle("PSI");

        Button refreshbutton = (Button) findViewById(R.id.refreshbutton1);
        southvalue = (TextView) findViewById(R.id.southvalue);
        northvalue = (TextView) findViewById(R.id.northvalue);
        eastvalue = (TextView) findViewById(R.id.eastvalue);
        westvalue = (TextView) findViewById(R.id.westvalue);
        centralvalue = (TextView) findViewById(R.id.centralvalue);
        nationalvalue = (TextView) findViewById(R.id.nationalvalue);

        dataService = new DataService(this);
        webService = new QueryWebService(this);

        checkConnection();

        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });
    }

    private void fetchData(){
        webService.getPM25(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("items");
                    JSONObject psi_twenty_four_hourly = arr.getJSONObject(0).
                            getJSONObject("readings").getJSONObject("psi_twenty_four_hourly");
                    String south = psi_twenty_four_hourly.getString("south");
                    String north = psi_twenty_four_hourly.getString("north");
                    String east = psi_twenty_four_hourly.getString("east");
                    String west = psi_twenty_four_hourly.getString("west");
                    String central = psi_twenty_four_hourly.getString("central");
                    String national = psi_twenty_four_hourly.getString("national");

                    southvalue.setText(south);
                    northvalue.setText(north);
                    eastvalue.setText(east);
                    westvalue.setText(west);
                    centralvalue.setText(central);
                    nationalvalue.setText(national);

                    PM25Data pm25Data = new PM25Data(south, north, east, west, central, national);
                    dataService.storePM25Data(pm25Data);

                } catch (JSONException e) {
                    e.toString();
                }
                String responseText = response.substring(0, 500);
                Log.d("PSI", responseText);
            }
        });
    }

    private void checkConnection(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            fetchData();
        }
        else {
            connected = false;
            List<PSIData> psiDataList = dataService.getPSIData();
            psiDataList.get(psiDataList.size()-1);
        }
    }
}