package com.example.hw7;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TrafficCamAsyncLoader extends AsyncTaskLoader<TrafficCamera[]> {

    public TrafficCamAsyncLoader(Context context) {
        super(context);

    }

    private static URL stringToURL(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String fetchData(URL url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String data = "";

        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            data = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public static TrafficCamera[] deserializeCamData(String jsonString){
        TrafficCamera[] cameras = null;

        try {

            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray features = rootObject.getJSONArray("Features");
            cameras = new TrafficCamera[features.length()];

            for (int i = 0; i < features.length(); i++) {
                JSONObject currentImage = features.getJSONObject(i);
                JSONArray coordinates = currentImage.getJSONArray("PointCoordinate");
                Double latitude = coordinates.getDouble(0);
                Double longitude = coordinates.getDouble(1);
                JSONArray cams = currentImage.getJSONArray("Cameras");
                JSONObject firstCam = cams.getJSONObject(0);
                String description = firstCam.getString("Description");
                String url = firstCam.getString("ImageUrl");
                String type = firstCam.getString("Type");
                TrafficCamera trafficCam = new TrafficCamera(description, url, type, latitude, longitude);
                cameras[i] = trafficCam;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cameras;
    }

    @Nullable
    @Override
    public TrafficCamera[] loadInBackground() {

        String baseURL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        URL strToURL = stringToURL(baseURL);

        String trafficCamData = this.fetchData(strToURL);

        TrafficCamera[] trafficCameras = this.deserializeCamData(trafficCamData);

        return trafficCameras;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

