package com.example.environmentreader.Utility;

import android.content.Context;
import com.example.environmentreader.BoxStoreProvider;
import com.example.environmentreader.Data.PM25Data;
import com.example.environmentreader.Data.PSIData;
import com.example.environmentreader.Data.TimeLogData;

import java.util.List;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class DataService {
    private BoxStore boxStore;
    private static DataService mInstance;

    public DataService(Context mContext) {
        boxStore = BoxStoreProvider.provide(mContext);
    }

    public List<PSIData> getPSIData( ){
        Box<PSIData> psiDataBox = boxStore.boxFor(PSIData.class);
        return psiDataBox.getAll();
    }
    public void storePSIData(PSIData data) {
        Box<PSIData> psiDataBox = boxStore.boxFor(PSIData.class);
        psiDataBox.put(data);
    }

    public List<PM25Data> getPM25Data( ) {
        Box<PM25Data> pm25DataBox = boxStore.boxFor(PM25Data.class);
        return pm25DataBox.getAll();
    }

    public void storePM25Data(PM25Data data) {
        Box<PM25Data> pm25DataBox = boxStore.boxFor(PM25Data.class);
        pm25DataBox.put(data);
    }

    public List<TimeLogData> getTimeLogData( ){
        Box<TimeLogData> timeLogDataBox = boxStore.boxFor(TimeLogData.class);
        return timeLogDataBox.getAll();
    }
    public void storeTimeLogData (TimeLogData time) {
        Box<TimeLogData> timeLogDataBox = boxStore.boxFor(TimeLogData.class);
        timeLogDataBox.put(time);
    }
}