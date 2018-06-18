package com.reuben.liquidpaytestapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.reuben.liquidpaytestapp.AlbumInterface;
import com.reuben.liquidpaytestapp.ConstantsManager;
import com.reuben.liquidpaytestapp.CustomApplication;
import com.reuben.liquidpaytestapp.JsonObject.Album;
import com.reuben.liquidpaytestapp.R;
import com.reuben.liquidpaytestapp.adapters.AlbumListViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Album_List extends Fragment {
    private ListView albumListView;
    private SwipeRefreshLayout swipeRefresh;
    private Call<List<Album>> apiCall;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = getArguments().getInt(ConstantsManager.INTENT_USER_ID);
        swipeRefresh = getView().findViewById(R.id.swipeRefresh);
        albumListView = getView().findViewById(R.id.album_list);
        albumListView.setNestedScrollingEnabled(true);
        albumListView.startNestedScroll(View.OVER_SCROLL_ALWAYS);
        initListeners();
        swipeRefresh.setRefreshing(true);
        retrieveAlbumData();
        ((AlbumInterface) getActivity()).updateAlbum(getResources().getString(R.string.album_activity_title));
    }

    private void initListeners() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveAlbumData();
            }
        });

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumListViewAdapter adapter = (AlbumListViewAdapter) parent.getAdapter();
                ((AlbumInterface) getActivity()).updateAlbum(getResources().getString(R.string.album_activity_title) + " - " + adapter.getItem(position).getTitle());
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantsManager.INTENT_ALBUM_ID, adapter.getItem(position).getId());
                Album_Details fragment = new Album_Details();
                fragment.setArguments(bundle);
                final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void retrieveAlbumData() {
        //Retrieve the list of Albums from the API
        apiCall = ((CustomApplication) getActivity().getApplication()).getLiquidClient().reposOfUserAlbum(userId);
        apiCall.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumListView.setAdapter(new AlbumListViewAdapter(getActivity(), response.body()));
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e("Failed to get Albums", t.getLocalizedMessage());
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_api_album), Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefresh.setRefreshing(false);
        if (apiCall.isExecuted() && !apiCall.isCanceled()) {
            apiCall.cancel();
        }
    }
}