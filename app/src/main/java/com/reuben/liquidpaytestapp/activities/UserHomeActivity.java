package com.reuben.liquidpaytestapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.reuben.liquidpaytestapp.ConstantsManager;
import com.reuben.liquidpaytestapp.CustomApplication;
import com.reuben.liquidpaytestapp.JsonObject.User;
import com.reuben.liquidpaytestapp.R;
import com.reuben.liquidpaytestapp.adapters.UserListViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefresh;
    private ListView userListView;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_home);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        userListView = findViewById(R.id.user_list);
        initListeners();
        swipeRefresh.setRefreshing(true);
        retrieveUserData();
    }

    private void initListeners(){
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveUserData();
            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserListViewAdapter adapter = (UserListViewAdapter)parent.getAdapter();
                startAlbumActivity(adapter.getItem(position));
            }
        });
    }

    private void retrieveUserData(){
        //Retrieve the list of user's from the API
        Call<List<User>> apiCall = ((CustomApplication)getApplication()).getLiquidClient().reposOfUser();
        apiCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userListView.setAdapter(new UserListViewAdapter(mContext,response.body()));
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Failed to retrieve list",t.getLocalizedMessage());
                Toast.makeText(mContext, mContext.getResources().getString(R.string.error_api_user),Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void startAlbumActivity(User data){
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(ConstantsManager.INTENT_USER_ID,data.getId());
        intent.putExtra(ConstantsManager.INTENT_NAME,data.getName());
        intent.putExtra(ConstantsManager.INTENT_USERNAME,data.getUsername());
        String address = "";
        address += data.getAddress().getStreet().isEmpty() ? "":" " + data.getAddress().getStreet();
        address += data.getAddress().getSuite().isEmpty() ? "":" " + data.getAddress().getSuite();
        address += data.getAddress().getCity().isEmpty() ? "":", " + data.getAddress().getCity();
        address += data.getAddress().getZipcode().isEmpty() ? "":", " + data.getAddress().getZipcode();
        intent.putExtra(ConstantsManager.INTENT_ADDRESS,address);
        intent.putExtra(ConstantsManager.INTENT_EMAIL,data.getEmail());
        startActivity(intent);
    }
}
