package com.project.moviebag.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.moviebag.models.Cast;
import com.project.moviebag.models.Crew;
import com.project.moviebag.models.Movie;
import com.project.moviebag.models.MovieModel;
import com.project.moviebag.models.Review;
import com.project.moviebag.repositories.MovieRepository;
import com.project.moviebag.response.MovieResponse;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    //this class is used for view-model


    private MovieRepository movieRepository;


    //constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getNowPlaying() {
        return movieRepository.getNowPlaying();
    }

    public LiveData<List<MovieModel>> getSimilarMovies() {
        return movieRepository.getSimilarMovies();
    }

    public LiveData<Movie> getSingleMovie() {
        return movieRepository.getSingleMovie();
    }

    public LiveData<List<Cast>> getCasts() {
        return movieRepository.getCasts();
    }

    public LiveData<List<Crew>> getDirector() {
        return movieRepository.getDirector();
    }

    public LiveData<List<Review>> getReviews() {
        return movieRepository.getReviews();
    }


    //calling the trending in view-model
    public void searchMovieApi(String query, int pageNumber) {
        movieRepository.searchMovieApi(query, pageNumber);
    }

    //calling the now Playing in view-model
    public void searchMovieApiNowPlaying(int pageNumber) {
        movieRepository.searchMovieApiNowPlaying(pageNumber);
    }

    //calling the single movie in view model
    public void searchMovieApiSingle(int movieId) {
        movieRepository.searchMovieSingle(movieId);
    }

    //calling the similar movie in view model
    public void searchMovieApiSimilar(int movieId, int pageNumber) {
        movieRepository.searchSimilarMovies(movieId, pageNumber);
    }

    //calling the cast in view-model
    public void searchMovieApiCast(int movie_id) {
        movieRepository.searchCasts(movie_id);
    }

    //calling the reviews in view-model
    public void searchMovieApiReview(int movie_id, int pageNumber) {
        movieRepository.searchReviews(movie_id, pageNumber);
    }

    //pagination Support
    public void searchNextPage() {
        movieRepository.searchNextPage();
    }

    public void searchNextPageSimilar() {
        movieRepository.searchNextPageSimilar();
    }

    public void searchNextPageReviews() {
        movieRepository.searchNextPageReviews();
    }

    //Clean the cast
    public void resetCast() {
        movieRepository.resetCast();
    }

    //Clean the review
    public void resetReview() {
        movieRepository.resetReview();
    }


    public LiveData<Boolean> getCastLoading() {
        return movieRepository.getCastLoading();
    }

    public LiveData<Boolean> getLoading() {
        return movieRepository.getLoading();
    }

}
