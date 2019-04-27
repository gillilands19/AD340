package com.example.hw3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.Gravity;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.util.Log;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOGTAG = "Logger... ";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        Intent intent = getIntent();
        String[] movieData = intent.getStringArrayExtra(MovieListActivity.MOVIE);

        setContentView(R.layout.movie_detail);

        TextView title = findViewById(R.id.movieTitle);
        TextView year = findViewById(R.id.year);
        TextView director = findViewById(R.id.director);
        TextView description = findViewById(R.id.description);


        title.setText(movieData[0]);
        year.setText(movieData[1]);
        director.setText(movieData[2]);
        description.setText(movieData[4]);
    }
}