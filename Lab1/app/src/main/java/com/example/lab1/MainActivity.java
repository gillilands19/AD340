package com.example.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final private static String TAG = "Logger....";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Main activity created");

        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Main Activity Started!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Main Activity R")
    }

    }
}
