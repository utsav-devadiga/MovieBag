package com.project.moviebag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.moviebag.adapters.MovieRecyclerViewAdapter;
import com.project.moviebag.adapters.NowPlayingAdapter;
import com.project.moviebag.adapters.OnMovieListener;
import com.project.moviebag.models.MovieModel;
import com.project.moviebag.request.Servicey;
import com.project.moviebag.response.MovieSearchResponse;
import com.project.moviebag.utils.Credentials;
import com.project.moviebag.utils.MovieApi;
import com.project.moviebag.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {


    RecyclerView moviesCycle;
    private MovieRecyclerViewAdapter adapter;
    ViewPager2 nowPlayingViewPager;
    private NowPlayingAdapter now_playing_adapter;
    TabLayout viewPagerIndicator;
    RelativeLayout loadingLayout;

    //View Model
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id binding
        moviesCycle = findViewById(R.id.movies_recyclerView);
        nowPlayingViewPager = findViewById(R.id.movies_viewPager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        loadingLayout  = findViewById(R.id.loading);

        //view-model
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //set up UI
        configureRecyclerView();
        configureViewPager();

        //calling the observers
        ObserveMovieTrendingChange();
        ObserveMovieNowPlayingChange();

        //api Calls
        searchMovieApi("", 1);
        searchMovieApiNowPlaying(1);


        movieListViewModel.getLoading().observe(this, loading -> {
            if (loading) {
                loadingLayout.setVisibility(View.VISIBLE);
            } else {
                loadingLayout.setVisibility(View.GONE);
            }
        });

    }


    //observing changes
    private void ObserveMovieTrendingChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observe any data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        //get the data in log

                        adapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    private void ObserveMovieNowPlayingChange() {
        movieListViewModel.getNowPlaying().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observe any data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        now_playing_adapter.setNowPlayingMovies(movieModels);
                    }
                }
            }
        });
    }

    //calling the trending in main-activity
    private void searchMovieApi(String query, int pageNumber) {
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    //calling the now-playing in main activity
    private void searchMovieApiNowPlaying(int pageNumber) {
        movieListViewModel.searchMovieApiNowPlaying(pageNumber);
    }

    private void configureRecyclerView() {
        adapter = new MovieRecyclerViewAdapter(this);
        moviesCycle.setAdapter(adapter);
        moviesCycle.setLayoutManager(new LinearLayoutManager(this));

        //Pagination support
        moviesCycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!moviesCycle.canScrollVertically(1)) {
                    //here we need to get another page of data
                    movieListViewModel.searchNextPage();
                }
            }
        });

    }

    private void configureViewPager() {
        now_playing_adapter = new NowPlayingAdapter(this);
        nowPlayingViewPager.setAdapter(now_playing_adapter);
        //indicator
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(viewPagerIndicator, nowPlayingViewPager, true, true, (tab, position) -> {
            //something to do that i am not sure of
        });
        tabLayoutMediator.attach();

    }
    @Override
    public void onMovieClick(int position) {
        //sending data to detail intent
        Intent detailIntent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
        detailIntent.putExtra("movie", adapter.getSelectedMovie(position));
        startActivity(detailIntent);

    }
    @Override
    public void onMovieClickNowPlaying(int position) {
        Intent detailIntent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
        detailIntent.putExtra("movie", now_playing_adapter.getSelectedMovie(position));
        startActivity(detailIntent);
    }

    /* private void getRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                        2);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    //Toast.makeText(MovieListActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies) {
                        Toast.makeText(MovieListActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Toast.makeText(MovieListActivity.this, "error " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }*/
}