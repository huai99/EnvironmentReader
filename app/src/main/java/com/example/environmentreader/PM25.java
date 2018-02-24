package com.example.environmentreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.environmentreader.Utility.QueryWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PM25 extends AppCompatActivity {

    TextView southvalue1, northvalue1, eastvalue1, westvalue1, centralvalue1, nationalvalue1;
    QueryWebService webService ;

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

        webService = new QueryWebService(PM25.this);
        fetchData();

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
                    JSONObject pm25_twenty_four_hourly = arr.getJSONObject(0).
                            getJSONObject("readings").getJSONObject("pm25_twenty_four_hourly");
                    String south = pm25_twenty_four_hourly.getString("south");
                    String north = pm25_twenty_four_hourly.getString("north");
                    String east = pm25_twenty_four_hourly.getString("east");
                    String west = pm25_twenty_four_hourly.getString("west");
                    String central = pm25_twenty_four_hourly.getString("central");
                    String national = pm25_twenty_four_hourly.getString("national");

                    southvalue1.setText(south);
                    northvalue1.setText(north);
                    eastvalue1.setText(east);
                    westvalue1.setText(west);
                    centralvalue1.setText(central);
                    nationalvalue1.setText(national);

                } catch (JSONException e) {
                    e.toString();
                }
                String responseText = response.substring(0, 500);
                Log.d("PSI", responseText);
            }
        });
    }
}