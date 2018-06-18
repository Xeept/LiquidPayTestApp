package com.reuben.liquidpaytestapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reuben.liquidpaytestapp.ConstantsManager;
import com.reuben.liquidpaytestapp.CustomApplication;
import com.reuben.liquidpaytestapp.JsonObject.AlbumPhoto;
import com.reuben.liquidpaytestapp.R;
import com.reuben.liquidpaytestapp.adapters.AlbumPhotosRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Album_Details extends Fragment{
    private RecyclerView detailsView;
    private SwipeRefreshLayout swipeRefresh;
    private Call<List<AlbumPhoto>> apiCall;
    private int albumId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        albumId = getArguments().getInt(ConstantsManager.INTENT_ALBUM_ID);
        swipeRefresh = getView().findViewById(R.id.swipeRefresh);
        detailsView = getView().findViewById(R.id.recyclerview);
        detailsView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        initListeners();
        swipeRefresh.setRefreshing(true);
        retrieveAlbumPhotoData();
    }

    private void initListeners(){
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveAlbumPhotoData();
            }
        });
    }

    private void retrieveAlbumPhotoData(){
        //Retrieve the list of Photos from the API
        apiCall = ((CustomApplication)getActivity().getApplication()).getLiquidClient().reposOfAlbumPhoto(albumId);
        apiCall.enqueue(new Callback<List<AlbumPhoto>>() {
            @Override
            public void onResponse(Call<List<AlbumPhoto>> call, Response<List<AlbumPhoto>> response) {
                detailsView.setAdapter(new AlbumPhotosRecyclerViewAdapter(getActivity(),response.body()));
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<AlbumPhoto>> call, Throwable t) {
                Log.e("Error getting details",t.getLocalizedMessage());
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_api_album_photo),Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefresh.setRefreshing(false);
        if(apiCall.isExecuted() && !apiCall.isCanceled()){
            apiCall.cancel();
        }
    }
}