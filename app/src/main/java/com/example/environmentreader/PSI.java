package com.example.environmentreader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.environmentreader.Data.TimeLogData;
import com.example.environmentreader.Utility.DataService;
import com.example.environmentreader.Utility.QueryWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PSI extends AppCompatActivity {

    TextView southvalue, northvalue, eastvalue, westvalue, centralvalue, nationalvalue, updatedtime;
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
        updatedtime = (TextView) findViewById(R.id.psitimeview);
        dataService = new DataService(this);
        webService = new QueryWebService(this);

        checkConnection();
        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.historymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int id = item.getItemId();

        if (id == R.id.history) {
            Intent intent = new Intent(PSI.this, History.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
        }


    private void fetchData(){
        webService.getPSI(new Response.Listener<String>() {
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

                    PSIData psiData = new PSIData(south, north, east, west, central, national);
                    dataService.storePSIData(psiData);

                } catch (JSONException e) {
                    e.toString();
                }
                String responseText = response.substring(0, 500);
                Log.d("PSI", responseText);
            }
        });
    }

    private void fetchTimeData(){
        webService.getTimeLog(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("items");
                    //JSONObject updatedtimestamp = arr.getJSONObject(0).getJSONObject("update_timestamp");
                    String timestamp = arr.getJSONObject(0).getString("update_timestamp");

                    updatedtime.setText("Last result: " + timestamp);

                    TimeLogData timeLogData = new TimeLogData(timestamp);
                    dataService.storeTimeLogData(timeLogData);

                }catch(JSONException e){
                    e.toString();
                }

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
            fetchTimeData();
        }
        else {
            connected = false;
            List<PSIData> psiDataList = dataService.getPSIData();
            PSIData data = psiDataList.get(psiDataList.size()-1);
            List<TimeLogData> timeLogDataList = dataService.getTimeLogData();
            TimeLogData timeLogData = timeLogDataList.get(timeLogDataList.size()-1);

            String south = data.getSouth();
            String north = data.getNorth();
            String east = data.getEast();
            String west = data.getWest();
            String central = data.getCentral();
            String national = data.getNational();
            String timestamp = timeLogData.getTimelog();

            updatedtime.setText("Last result: " + timestamp);
            southvalue.setText(south);
            northvalue.setText(north);
            eastvalue.setText(east);
            westvalue.setText(west);
            centralvalue.setText(central);
            nationalvalue.setText(national);

        }
    }
}