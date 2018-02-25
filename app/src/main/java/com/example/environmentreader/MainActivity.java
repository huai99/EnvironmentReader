package com.example.environmentreader;

import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.environmentreader.Data.PM25Data;
import com.example.environmentreader.Data.PSIData;
import com.example.environmentreader.Data.TimeLogData;
import com.example.environmentreader.Utility.DataService;
import com.example.environmentreader.Utility.QueryWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView psivalue, pm25value, updatedtime;
    QueryWebService webService;
    DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        Button refreshbutton = (Button) findViewById(R.id.refreshbutton);
        psivalue = (TextView) findViewById(R.id.psivalue);
        pm25value = (TextView) findViewById(R.id.pm25value);
        updatedtime = (TextView) findViewById(R.id.timeview);

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

    private void fetchPSIData(){
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

                    psivalue.setText(national);

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

    private void fetchPM25Data(){
        webService.getPM25(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("items");
                    JSONObject pm25_twenty_four_hourly = arr.getJSONObject(0).
                            getJSONObject("readings").getJSONObject("pm25_twenty_four_hourly");
                    String south1 = pm25_twenty_four_hourly.getString("south");
                    String north1 = pm25_twenty_four_hourly.getString("north");
                    String east1 = pm25_twenty_four_hourly.getString("east");
                    String west1 = pm25_twenty_four_hourly.getString("west");
                    String central1 = pm25_twenty_four_hourly.getString("central");
                    String national1 = pm25_twenty_four_hourly.getString("national");

                    pm25value.setText(national1);

                    PM25Data pm25Data = new PM25Data(south1, north1, east1, west1, central1, national1);
                    dataService.storePM25Data(pm25Data);

                } catch (JSONException e) {
                    e.toString();
                }
                String responseText = response.substring(0, 500);
                Log.d("PM25", responseText);
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
            fetchPSIData();
            fetchPM25Data();
            fetchTimeData();
        }
        else {
            connected = false;
            List<PSIData> psiDataList = dataService.getPSIData();
            PSIData data = psiDataList.get(psiDataList.size()-1);;
            List<PM25Data> pm25DataList = dataService.getPM25Data();
            PM25Data data2 = pm25DataList.get(pm25DataList.size()-1);;
            List<TimeLogData> timeLogDataList = dataService.getTimeLogData();
            TimeLogData timeLogData = timeLogDataList.get(timeLogDataList.size()-1);

            String national = data.getNational();
            psivalue.setText(national);
            String national1 = data2.getNational();
            pm25value.setText(national1);
            String timestamp = timeLogData.getTimelog();
            updatedtime.setText("Last result: " + timestamp);
        }
    }

}
