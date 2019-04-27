package com.example.hw3;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.BackgroundColorSpan;
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
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;

public class MovieRecyclerAdapter extends
        RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private String[][] movies;
    private LayoutInflater movieInflater;
    private ItemClickListener movieClickListener;

    // data is passed into the constructor
    MovieRecyclerAdapter(Context context, String[][] data) {
        this.movieInflater = LayoutInflater.from(context);
        this.movies = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieHolder = movieInflater.inflate(R.layout.movie_recycler_item, parent, false);
        return new ViewHolder(movieHolder);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] currentMovie = movies[position];
        String title = currentMovie[0];
        String year = currentMovie[1];
        String gridText = title + "\n (" + year + ")";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bgImage = getBitmapFromURL(currentMovie[3]);
        Drawable bgDraw = new BitmapDrawable(holder.movieTextView.getContext().getResources(), bgImage);

        holder.movieTextView.setBackground(bgDraw);
        Spannable highlightedText = new SpannableString(gridText);
        highlightedText.setSpan(new BackgroundColorSpan(0xCCCCCCCC), 0, gridText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.movieTextView.setText(highlightedText);

    }

    @Override
    public int getItemCount() {
        return movies.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTextView;

        ViewHolder(View itemView) {
            super(itemView);
            movieTextView = itemView.findViewById(R.id.title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (movieClickListener != null)
                movieClickListener.onItemClick(view, getAdapterPosition());
        }
    }

   String[] getItem(int position) {
        return this.movies[position];
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.movieClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    //Grabbed this method from a tutorial. only problem is it is blocking.
    // takes forever to grab each image and open the activity.

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}