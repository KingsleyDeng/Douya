package com.kingsley.douya.net;

import com.kingsley.douya.model.MovieDetailModel;
import com.kingsley.douya.model.MovieModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface DouBanApi {

    @GET("top250")
    Observable<MovieModel> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("search")
    Observable<MovieModel> searchKeyword(@Query("q") String q, @Query("start") int start, @Query("count") int count);

    @GET("search")
    Observable<MovieModel> searchTag(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("subject/{id}")
    Observable<MovieDetailModel> getMovieDetail(@Path("id") String id);

}
