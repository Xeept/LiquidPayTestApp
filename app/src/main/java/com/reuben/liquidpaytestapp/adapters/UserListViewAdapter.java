package com.reuben.liquidpaytestapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reuben.liquidpaytestapp.JsonObject.User;
import com.reuben.liquidpaytestapp.R;

import java.util.List;

public class UserListViewAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    public UserListViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_list,parent, false);
        }

        User userItem = getItem(position);
        TextView nameText = convertView.findViewById(R.id.name);
        TextView userNameText =convertView.findViewById(R.id.username);
        TextView phoneNumberText = convertView.findViewById(R.id.phone_number);

        nameText.setText(userItem.getName());
        userNameText.setText(userItem.getUsername());
        phoneNumberText.setText(userItem.getPhone());

        return convertView;
    }
}
