package com.reuben.liquidpaytestapp.retroInterface;

import com.reuben.liquidpaytestapp.JsonObject.Album;
import com.reuben.liquidpaytestapp.JsonObject.AlbumPhoto;
import com.reuben.liquidpaytestapp.JsonObject.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiquidClient {
    @GET("/users")
    Call<List<User>> reposOfUser();

    @GET("/albums")
    Call<List<Album>> reposOfUserAlbum(@Query("userId") int userId);

    @GET("/photos")
    Call<List<AlbumPhoto>> reposOfAlbumPhoto(@Query("albumId") int albumId);
}
