package com.arif.cptest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arif.cptest.R;
import com.arif.cptest.activities.InstructionsActivity;
import com.arif.cptest.activities.MovieSearchActivity;
import com.arif.cptest.adapters.MoviesRecyclerAdapter;
import com.arif.cptest.adapters.SearchTitlesAdapter;
import com.arif.cptest.models.Movie;
import com.arif.cptest.models.Search;
import com.arif.cptest.models.SearchResults;
import com.arif.cptest.networking.OmbApiObservables;
import com.arif.cptest.utils.CPUtils;
import com.arif.cptest.utils.SpacesItemDecoration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieSearchFragment extends BaseFragment implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private static final CharSequence[] SELECTION_KEYS = {"All", "Movie", "Series"};
    private static final CharSequence[] SELECTION_VALUE = {"", "movie", "series"};
    private RecyclerView mMovieResultsRecyclerView;
    private RecyclerView.LayoutManager mVerticalLayoutManager;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private List<Search> mSearchList;
    private List<Movie> mMovies;
    private ProgressDialog mPd;
    private Subscription allMoviesForTitlesubscription, searchResultsSubscription, movieFromTitleSubscription, multiQuerySubscription;
    private SearchView searchView;
    private SearchTitlesAdapter mSearchViewAdapter;
    private MatrixCursor matrixCursor;
    private OmbApiObservables mOmdbApiObservables;
    private FloatingActionButton fab;
    private volatile String mFilterSelection = "";
    private volatile int mSelectedFilterPosition;

    public MovieSearchFragment() {
        mMovies = new ArrayList<>();
        mSearchList = new ArrayList<>();
        mOmdbApiObservables = new OmbApiObservables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mMovieResultsRecyclerView = (RecyclerView) getView().findViewById(R.id.movie_results_recycler_view);
        mMovieResultsRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        mVerticalLayoutManager = new LinearLayoutManager(getActivity());
        moviesRecyclerAdapter = new MoviesRecyclerAdapter(getActivity(), mMovies);
        mMovieResultsRecyclerView.setLayoutManager(mVerticalLayoutManager);
        mMovieResultsRecyclerView.setAdapter(moviesRecyclerAdapter);
        mPd = new ProgressDialog(getActivity());
        mPd.setTitle("Please wait, loading data");
        mPd.setIndeterminate(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = ((MovieSearchActivity) getActivity()).getFloatingActionBar();
        fab.setOnClickListener(view -> showFilterSelectionDialog());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        try {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint("Search by");
            searchView.setOnQueryTextListener(this);
            searchView.setOnSuggestionListener(this);
            matrixCursor = new MatrixCursor(CPUtils.COLUMNS);
            mSearchViewAdapter = new SearchTitlesAdapter(getActivity(), R.layout.search_auto_complete_item_layout, matrixCursor, CPUtils.COLUMNS, null, -1100);
            searchView.setSuggestionsAdapter(mSearchViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                startActivity(new Intent(getActivity(), InstructionsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (allMoviesForTitlesubscription != null && !allMoviesForTitlesubscription.isUnsubscribed())
            allMoviesForTitlesubscription.unsubscribe();
        if (searchResultsSubscription != null && !searchResultsSubscription.isUnsubscribed())
            searchResultsSubscription.unsubscribe();
        if (movieFromTitleSubscription != null && !movieFromTitleSubscription.isUnsubscribed())
            movieFromTitleSubscription.unsubscribe();
        if (multiQuerySubscription != null && !multiQuerySubscription.isUnsubscribed())
            multiQuerySubscription.unsubscribe();
        if (!matrixCursor.isClosed())
            matrixCursor.close();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 2) {
            if (mMovies.size() > 0) {
                mMovies.clear();
                moviesRecyclerAdapter.notifyDataSetChanged();
            }
            mPd.show();
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                searchView.clearFocus();
                CPUtils.hideSoftKeyboard((AppCompatActivity) getActivity());
                if (query.contains("__")) {
                    String[] queries = query.split("__");
                    Observable<List<Movie>> observable = mOmdbApiObservables.getMoviesForMultipleQueries(Arrays.asList(queries), mFilterSelection);
                    multiQuerySubscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(moviesForMultiQuerySearchSubscriber());
                } else {
                    Observable<List<Movie>> observable = mOmdbApiObservables.getAllMoviesForSearchApi(encodedQuery, mFilterSelection);
                    allMoviesForTitlesubscription = observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(moviesForSearchSubscriber());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            showShortToast("Please enter a title which is greater than 2 chars.");
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 2) {
            try {
                if (searchResultsSubscription != null && !searchResultsSubscription.isUnsubscribed()) {
                    //Cancel all ongoing requests and change cursor
                    searchResultsSubscription.unsubscribe();
                    matrixCursor = CPUtils.convertResultsToCursor(new ArrayList<>());
                    mSearchViewAdapter.changeCursor(matrixCursor);
                }
                String encodedQuery = URLEncoder.encode(newText, "UTF-8");
                Observable<SearchResults> observable = mOmdbApiObservables.getSearchResultsApi(encodedQuery, mFilterSelection);
                searchResultsSubscription = observable
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(searchResultsSubscriber());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /*******
     * Suggestion listeners
     *******/

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        String movieTitle = cursor.getString(4);
        searchView.setQuery(movieTitle, false);
        searchView.clearFocus();
        if (mMovies != null && mMovies.size() > 0) {
            mMovies.clear();
            moviesRecyclerAdapter.notifyDataSetChanged();
        }
        CPUtils.hideSoftKeyboard((AppCompatActivity) getActivity());
        Observable<Movie> observable = mOmdbApiObservables.getSingleMovieForTitleApi(movieTitle, mFilterSelection);
        movieFromTitleSubscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(movieFromTitleSubscriber());
        return true;
    }

    /***
     * All Rx Subscribers
     ****/

    private Subscriber<List<Movie>> moviesForSearchSubscriber() {
        return new Subscriber<List<Movie>>() {
            @Override
            public void onCompleted() {
                if (mPd.isShowing())
                    mPd.dismiss();
                moviesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                if (mPd.isShowing())
                    mPd.dismiss();
                HttpException exception = (HttpException) e;
                Log.e(MovieSearchFragment.class.getName(), "Error: " + exception.code());
            }

            @Override
            public void onNext(List<Movie> movies) {
                if (movies == null || movies.size() == 0)
                    showShortToast("No results, is your title correct?");
                for (Movie m : movies) {
                    mMovies.add(m);
                }
            }
        };
    }

    private Subscriber<List<Movie>> moviesForMultiQuerySearchSubscriber() {
        return new Subscriber<List<Movie>>() {
            @Override
            public void onCompleted() {
                if (mPd.isShowing())
                    mPd.dismiss();
                moviesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                if (mPd.isShowing())
                    mPd.dismiss();
                HttpException exception = (HttpException) e;
                Log.e(MovieSearchFragment.class.getName(), "Error: " + exception.code());
            }

            @Override
            public void onNext(List<Movie> movies) {
                if (movies == null || movies.size() == 0)
                    showShortToast("No results, is your title correct?");
                for (Movie m : movies) {
                    mMovies.add(m);
                }
            }
        };
    }

    private Subscriber<SearchResults> searchResultsSubscriber() {
        return new Subscriber<SearchResults>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                HttpException exception = (HttpException) e;
                Log.e(MovieSearchFragment.class.getName(), "Error: " + exception.code());
            }

            @Override
            public void onNext(SearchResults searchResults) {
                MatrixCursor matrixCursor = CPUtils.convertResultsToCursor(searchResults.getSearch());
                mSearchViewAdapter.changeCursor(matrixCursor);
            }
        };
    }

    private Subscriber<Movie> movieFromTitleSubscriber() {
        return new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
                moviesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                HttpException exception = (HttpException) e;
                Log.e(MovieSearchFragment.class.getName(), "Error: " + exception.code());
            }

            @Override
            public void onNext(Movie movie) {
                mMovies.add(movie);
            }
        };
    }

    private void showFilterSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter Criteria");
        builder.setSingleChoiceItems(SELECTION_KEYS, mSelectedFilterPosition, (dialogInterface, i) -> {
            mFilterSelection = SELECTION_VALUE[i].toString();
            mSelectedFilterPosition = i;
            dialogInterface.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
