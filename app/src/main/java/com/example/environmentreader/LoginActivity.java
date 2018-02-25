package com.example.environmentreader;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    EditText name, password;
    Button savebutton, nextbutton;
    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.pw);
        savebutton = (Button) findViewById(R.id.savebutton);
        nextbutton = (Button) findViewById(R.id.nextbutton);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savelogin();
            }});
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(LoginActivity.this, Homepage.class);
                startActivity(nextActivity);
            }
        });

        displaylogin( );
    }

    private void savelogin(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String n  = name.getText().toString();
        String pw  = password.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Name, n);
        editor.putString(Password, pw);
        editor.apply();

        name.setText(n);
        password.setText(pw);
    }

    public void displaylogin( ) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Name)) {
            name.setText(sharedpreferences.getString(Name, ""));
        }
        if (sharedpreferences.contains(Password)) {
            password.setText(sharedpreferences.getString(Password, ""));
        }
    }

}
