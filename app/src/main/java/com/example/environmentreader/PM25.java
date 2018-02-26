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
import com.android.volley.Response;
import com.example.environmentreader.Data.PM25Data;
import com.example.environmentreader.Utility.QueryWebService;
import com.example.environmentreader.Utility.DataService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;


public class PM25 extends AppCompatActivity {

    TextView southvalue1, northvalue1, eastvalue1, westvalue1, centralvalue1, nationalvalue1, updatedtime;
    QueryWebService webService;
    DataService dataService;
    History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25);
        Intent intent = getIntent();
        getSupportActionBar().setTitle("PM 25");

        Button refreshbutton = (Button) findViewById(R.id.refreshbutton2);
        southvalue1 = (TextView) findViewById(R.id.southvalue1);
        northvalue1 = (TextView) findViewById(R.id.northvalue1);
        eastvalue1 = (TextView) findViewById(R.id.eastvalue1);
        westvalue1 = (TextView) findViewById(R.id.westvalue1);
        centralvalue1 = (TextView) findViewById(R.id.centralvalue1);
        nationalvalue1 = (TextView) findViewById(R.id.nationalvalue1);
        updatedtime = (TextView) findViewById(R.id.pm25timevalue);

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
            Intent intent = new Intent(PM25.this, History.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void fetchData() {
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
                    String timestamp1 = arr.getJSONObject(0).getString("update_timestamp");

                    updatedtime.setText("Last result: " + timestamp1);
                    southvalue1.setText(south1);
                    northvalue1.setText(north1);
                    eastvalue1.setText(east1);
                    westvalue1.setText(west1);
                    centralvalue1.setText(central1);
                    nationalvalue1.setText(national1);

                    PM25Data pm25Data = new PM25Data(south1, north1, east1, west1, central1, national1, timestamp1);
                    dataService.storePM25Data(pm25Data);

                } catch (JSONException e) {
                    e.toString();
                }
                String responseText = response.substring(0, 500);
                Log.d("PM25", responseText);
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
            List<PM25Data> pm25DataList = dataService.getPM25Data();
            PM25Data data = pm25DataList.get(pm25DataList.size()-1);;

            String south1 = data.getSouth();
            String north1 = data.getNorth();
            String east1 = data.getEast();
            String west1 = data.getWest();
            String central1 = data.getCentral();
            String national1 = data.getNational();
            String timestamp1 = data.getPm25time();

            updatedtime.setText("Last result: " + timestamp1);
            southvalue1.setText(south1);
            northvalue1.setText(north1);
            eastvalue1.setText(east1);
            westvalue1.setText(west1);
            centralvalue1.setText(central1);
            nationalvalue1.setText(national1);
        }
    }
}