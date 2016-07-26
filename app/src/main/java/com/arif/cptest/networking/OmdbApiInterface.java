package com.arif.cptest.networking;

import com.arif.cptest.models.Movie;
import com.arif.cptest.models.SearchResults;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by arifnadeem on 11/2/15.
 */
public interface OmdbApiInterface {

    @GET("/")
    Observable<SearchResults> getSearchResults(@Query("s") String query,
                                               @Query("plot") String plot,
                                               @Query("type") String type,
                                               @Query("r") String format);

    @GET("/")
    Observable<Movie> getMovie(@Query("t") String title,
                               @Query("plot") String plot,
                               @Query("type") String type,
                               @Query("r") String format);

}
