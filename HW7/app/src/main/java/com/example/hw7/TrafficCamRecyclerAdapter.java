package com.example.hw7;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TrafficCamRecyclerAdapter extends RecyclerView.Adapter<TrafficCamRecyclerAdapter.ViewHolder>
        implements LoaderManager.LoaderCallbacks<String>  {

    private ItemClickListener listener;
    private LayoutInflater camInflater;
    private TrafficCamera[] trafficCams;


    public TrafficCamRecyclerAdapter(Context context, TrafficCamera[] trafficCams) {
        this.trafficCams = trafficCams;
        this.camInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View camHolder = camInflater.inflate(R.layout.cam_recyclerview_item, parent, false);
        return new ViewHolder(camHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TrafficCamera trafficCam = trafficCams[position];
        holder.camTextView.setText(trafficCam.getDescription());
        Picasso.get().load(trafficCam.getUrl()).into(holder.camImageView);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView camTextView;
        ImageView camImageView;

        ViewHolder(View itemView) {
            super(itemView);
            camTextView = itemView.findViewById(R.id.cam_info_text);
            camImageView = itemView.findViewById(R.id.cam_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null)
                listener.onItemClick(view, getAdapterPosition());
        }
    }

    TrafficCamera getItem(int position) { return this.trafficCams[position]; }

    void setClickListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return trafficCams.length;
    }


}
