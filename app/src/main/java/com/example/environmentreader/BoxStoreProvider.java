package com.example.environmentreader;

import android.content.Context;

import com.example.environmentreader.Data.MyObjectBox;

import io.objectbox.BoxStore;

public class BoxStoreProvider {

    private static BoxStore boxStore;

    public static BoxStore provide(Context context) {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder().androidContext(context).build();
            return boxStore;
        } else {
            return boxStore;
        }

    }

}