package com.reuben.liquidpaytestapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reuben.liquidpaytestapp.JsonObject.Album;
import com.reuben.liquidpaytestapp.R;

import java.util.List;

public class AlbumListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Album> albumList;

    public AlbumListViewAdapter(Context context, List<Album> userList) {
        this.context = context;
        this.albumList = userList;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Album getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return albumList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.album_list,parent, false);
        }

        ((TextView)convertView.findViewById(R.id.name)).setText(getItem(position).getTitle());
        return convertView;
    }
}
