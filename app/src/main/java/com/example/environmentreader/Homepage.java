package com.example.environmentreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    Button home, psi, pm25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button home = (Button) findViewById(R.id.button);
        Button psi = (Button) findViewById(R.id.button2);
        Button pm25 = (Button) findViewById(R.id.button3);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(Homepage.this, MainActivity.class);
                startActivity(nextActivity);
            }
        });

        psi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(Homepage.this, PSI.class);
                startActivity(nextActivity);
            }
        });

        pm25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(Homepage.this, PM25.class);
                startActivity(nextActivity);
            }
        });
    }
}
