package com.example.hw6;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TrafficCamAsyncLoader extends AsyncTaskLoader<String> {

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

    @Nullable
    @Override
    public String loadInBackground() {

        String baseURL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        URL strToURL = stringToURL(baseURL);

        String trafficCamData = this.fetchData(strToURL);

        return trafficCamData;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
