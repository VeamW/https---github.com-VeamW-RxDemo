package com.arif.cptest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arif.cptest.R;
import com.arif.cptest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arifnadeem on 11/2/15.
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    public MoviesRecyclerAdapter(Context cxt, List<Movie> movies) {
        mContext = cxt;
        mMovies = movies;
    }

    @Override
    public MoviesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_movie_layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesRecyclerAdapter.ViewHolder holder, int position) {
        String movieTitle = mMovies.get(position).getTitle();
        String type = mMovies.get(position).getType();
        if (movieTitle != null)
            holder.mTvMovieTitle.setText(String.format("%s (%s)", movieTitle, (type != null ? type : "N/A")));
        String poster = mMovies.get(position).getPoster();
        if (poster != null)
            Picasso.with(mContext).load(poster).fit().centerCrop().error(R.drawable.image_not_available).into(holder.mMoviePoster);
        String movieGenre = mMovies.get(position).getGenre();
        if (movieGenre != null)
            holder.mTvMovieGenre.setText(movieGenre);
        String movieReleaseDate = mMovies.get(position).getReleased();
        if (movieReleaseDate != null)
            holder.mTvMovieReleaseDate.setText(String.format("%s %s", "Released On:", movieReleaseDate));
        String movieCast = mMovies.get(position).getActors();
        if (movieCast != null)
            holder.mTvMovieCast.setText(String.format("%s %s", "Cast:", movieCast));
        String moviePlot = mMovies.get(position).getPlot();
        if (moviePlot != null)
            holder.mTvMoviePlot.setText(String.format("\"%s\"", moviePlot));
        String movieRating = mMovies.get(position).getImdbRating();
        if (movieRating != null) {
            holder.mMovieRating.setText(String.format("%s %s", "IMDB Rating:", movieRating));
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMoviePoster;
        public TextView mTvMovieTitle, mTvMovieGenre, mTvMovieReleaseDate, mTvMoviePlot, mMovieRating, mTvMovieCast;

        public ViewHolder(View base) {
            super(base);
            mTvMovieTitle = (TextView) base.findViewById(R.id.movieTitle);
            mMoviePoster = (ImageView) base.findViewById(R.id.moviePoster);
            mTvMovieGenre = (TextView) base.findViewById(R.id.movieGenre);
            mTvMovieReleaseDate = (TextView) base.findViewById(R.id.movieReleaseDate);
            mTvMoviePlot = (TextView) base.findViewById(R.id.moviePlot);
            mMovieRating = (TextView) base.findViewById(R.id.movieRating);
            mTvMovieCast = (TextView) base.findViewById(R.id.movieCast);
        }
    }
}
