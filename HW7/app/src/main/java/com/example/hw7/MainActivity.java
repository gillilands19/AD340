package com.example.hw7;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<TrafficCamera[]> {

    private static final String LOGTAG = "LOGGER...";
    private TrafficCamera[] trafficCamArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle bundle = new Bundle();
            getSupportLoaderManager().restartLoader(0, bundle, this);
        } else {
            Toast toast = Toast.makeText(this, "A Network Error Occurred!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @NonNull
    @Override
    public Loader<TrafficCamera[]> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new TrafficCamAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<TrafficCamera[]> loader, TrafficCamera[] trafficCams) {
        this.trafficCamArray = trafficCams;
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager gridManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridManager);

        TrafficCamRecyclerAdapter adapter = new TrafficCamRecyclerAdapter(getApplicationContext(), trafficCamArray);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new TrafficCamRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("TRAFFICCAM", trafficCamArray[position]);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLoaderReset(@NonNull Loader<TrafficCamera[]> loader) {

    }
}
