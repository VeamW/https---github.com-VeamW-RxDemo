package com.arif.cptest.networking;

import com.arif.cptest.models.Movie;
import com.arif.cptest.models.SearchResults;
import com.arif.cptest.singletons.RetrofitHelper;

import java.util.Collections;
import java.util.List;

import retrofit.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by arifnadeem on 11/5/15.
 */
public class OmbApiObservables {

    private Retrofit mRetrofit;
    private RetrofitHelper mRetrofitHelper;
    private OmdbApiInterface apiInterface;

    public OmbApiObservables() {
        mRetrofitHelper = RetrofitHelper.getInstance();
        mRetrofit = mRetrofitHelper.getRetrofit();
        apiInterface = mRetrofit.create(OmdbApiInterface.class);
    }

    public Observable<List<Movie>> getMoviesForMultipleQueries(List<String> queries, String type) {
        Observable<List<Movie>> observable = Observable.from(queries).flatMap(query -> getAllMoviesForSearchApi(query.trim(), type)).subscribeOn(Schedulers.newThread());
        return observable;
    }

    public Observable<List<Movie>> getAllMoviesForSearchApi(String query, String type) {
        return apiInterface.getSearchResults(query, "short", type, "json").subscribeOn(Schedulers.newThread())
                .flatMap(searchResults -> Observable.from(searchResults.getSearch() != null ? searchResults.getSearch() : Collections.emptyList()))
                .flatMap(search -> getSingleMovieForTitleApi(search.getTitle(), type)).toList();
    }

    public Observable<Movie> getSingleMovieForTitleApi(String title, String type) {
        return apiInterface.getMovie(title, "short", type, "json").subscribeOn(Schedulers.newThread());
    }

    public Observable<SearchResults> getSearchResultsApi(String query, String type) {
        return apiInterface.getSearchResults(query, "short", type, "json");
    }

}
