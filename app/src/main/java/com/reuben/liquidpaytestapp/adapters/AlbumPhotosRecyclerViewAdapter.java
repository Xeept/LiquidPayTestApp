package com.reuben.liquidpaytestapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.reuben.liquidpaytestapp.JsonObject.AlbumPhoto;
import com.reuben.liquidpaytestapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumPhotosRecyclerViewAdapter extends RecyclerView.Adapter<AlbumPhotosRecyclerViewAdapter.AlbumPhotos> {
    private List<AlbumPhoto> albumPhotos;
    private Context context;

    public AlbumPhotosRecyclerViewAdapter(Context context, List<AlbumPhoto> albumPhotos) {
        this.albumPhotos = albumPhotos;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumPhotos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_album_details,parent,false);
        return new AlbumPhotos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumPhotos holder, int position) {
        Picasso.get().load(albumPhotos.get(position).getUrl()).placeholder(R.drawable.ic_placeholder).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return albumPhotos.size();
    }

    public class AlbumPhotos extends RecyclerView.ViewHolder {
        private ImageView image;

        public AlbumPhotos(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
