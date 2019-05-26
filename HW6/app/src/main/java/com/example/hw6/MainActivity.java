package com.example.hw6;

import android.content.Context;
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
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

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
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new TrafficCamAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {

        try {

            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray features = rootObject.getJSONArray("Features");
            TrafficCamera[] cameras = new TrafficCamera[features.length()];

            for (int i = 0; i < features.length(); i ++) {
                JSONObject currentImage = features.getJSONObject(i);
                JSONArray cams = currentImage.getJSONArray("Cameras");
                JSONObject firstCam = cams.getJSONObject(0);
                String description = firstCam.getString("Description");
                String url = firstCam.getString("ImageUrl");
                String type = firstCam.getString("Type");
                TrafficCamera trafficCam = new TrafficCamera(description, url, type);
                cameras[i] = trafficCam;
            }

            RecyclerView recyclerView = findViewById(R.id.recyclerview);

            GridLayoutManager gridManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(gridManager);

            TrafficCamRecyclerAdapter adapter = new TrafficCamRecyclerAdapter(getApplicationContext(), cameras);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
