package com.example.hw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.Toast;
import android.view.Gravity;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = "LOGGER... ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOGTAG, this.getLifecycle()
                .getCurrentState()
                .toString());

        Button movieListButton = findViewById(R.id.movieListButton);
        movieListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieListIntent = new Intent(getApplicationContext(), MovieListActivity.class);
                startActivity(movieListIntent);
            }
        });
    }

    protected void onToastClick(View toastButton) {
        Log.i(LOGTAG, "Button " + toastButton.getId() + " clicked.");

        Button clickedBtn = (Button)findViewById(toastButton.getId());

        Log.i(LOGTAG, "Button " + clickedBtn.getText().toString() + " clicked.");

        CharSequence toastText = "You Clicked: " + clickedBtn.getText().toString();

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast btnToast = Toast.makeText(context, toastText, duration);

        btnToast.show();


    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOGTAG, "started");
        Log.i(LOGTAG, this.getLifecycle()
                .getCurrentState()
                .toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOGTAG, "resumed");
        Log.i(LOGTAG, this.getLifecycle()
                .getCurrentState()
                .toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOGTAG, "stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOGTAG, "destroyed");
    }
}