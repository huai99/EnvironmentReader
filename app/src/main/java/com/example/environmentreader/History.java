package com.example.environmentreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentreader.Data.TimeLogData;
import com.example.environmentreader.Utility.DataService;

import java.util.List;


public class History extends AppCompatActivity{

    ListView list;
    ArrayAdapter adapter;
    String historyarray[] = {"Time1", "Time2"};
    DataService dataService;
    TextView historylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Intent intent = getIntent();

        list = (ListView)findViewById(R.id.HistoryListview);
        historylist = (TextView) findViewById(R.id.historylist);

//        List<TimeLogData> timeLogDataList = dataService.getTimeLogData();
//        TimeLogData timeLogData = timeLogDataList.get(timeLogDataList.size()-1);
//        String timestamp = timeLogData.getTimelog();
//        historylist.setText(timestamp);
//        historyarray [] = list.toString();
//        int[] array = new int[list.size()];
//        for(int i = 0; i < list.size(); i++) array[i] = list.get(i);

        adapter = new ArrayAdapter<String>(this, R.layout.historylist, R.id.historylist, historyarray);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }
} 