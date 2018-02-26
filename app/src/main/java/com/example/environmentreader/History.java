package com.example.environmentreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentreader.Data.PM25Data;
import com.example.environmentreader.Data.PSIData;
import com.example.environmentreader.Utility.DataService;

import java.util.List;


public class History extends AppCompatActivity{

    ListView list;
    ArrayAdapter adapter;
    String historyarray[] ;// {"Time1", "Time2"}
    DataService dataService;
    TextView historylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Intent intent = getIntent();

        list = (ListView)findViewById(R.id.HistoryListview);
        historylist = (TextView) findViewById(R.id.historylist);

        List<PSIData> psiDataList = dataService.getPSIData();
//        PSIData data1 = psiDataList.get(psiDataList.size());
//        List<PM25Data> pm25DataList = dataService.getPM25Data();
//        PM25Data data2 = pm25DataList.get(pm25DataList.size());;
//        String timestamp = data1.getPsitime();
//        historylist.setText(timestamp);
//        historyarray[] = list.toString();
//        int[] array = new int[list.size()];
//        for(int i = 0; i < list.size(); i++) array[i] = list.get(i);

//        adapter = new ArrayAdapter<String>(this, R.layout.historylist, R.id.historylist, historyarray);
//        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }
} 