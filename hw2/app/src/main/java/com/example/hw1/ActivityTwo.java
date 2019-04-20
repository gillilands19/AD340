package com.example.hw1;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;


public class ActivityTwo extends Activity {

    private static final String LOGTAG = "LOGGER.... ";

    public static final String RESULT = "my.response";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        Intent intent = getIntent();

        String actionMessage = intent.getStringExtra(MainActivity.MESSAGE_ID);

        setContentView(R.layout.activity_two);

        TextView label = (TextView) findViewById(R.id.intent_message);
        label.setText(actionMessage);

        Log.i(LOGTAG, "onCreate");

    }

    protected void onClickMain(View view) {
        EditText textBox = (EditText) findViewById(R.id.response);
        String message = textBox.getText().toString();

        Intent response = new Intent();
        response.putExtra(ActivityTwo.RESULT, message);

        setResult(Activity.RESULT_OK, response);
        Log.i(LOGTAG, "clicked Main Button");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOGTAG, "started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOGTAG, "resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOGTAG, "paused");
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