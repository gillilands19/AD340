package com.example.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = "LOGGER... ";
    private static final int RESULT_ID = 1;

    public static final String MESSAGE_ID = "my.message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOGTAG, this.getLifecycle()
                .getCurrentState()
                .toString());
    }

    protected void onClick(View button) {
        Log.i(LOGTAG, "Button " + button.getId() + " clicked.");

        EditText textBox = (EditText)findViewById(R.id.message_box);
        String message = textBox.getText().toString();

        Intent intent = new Intent(this, ActivityTwo.class);

        intent.putExtra(MESSAGE_ID, message);

        startActivityForResult(intent, RESULT_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_ID) {
            if (resultCode == RESULT_OK) {
                TextView label = (TextView)findViewById(R.id.message);
                String message = label.getText().toString();
                message += "\n\n" + data.getStringExtra(ActivityTwo.RESULT);
                label.setText(message);
            }
        }
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