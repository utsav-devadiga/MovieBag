package com.project.moviebag.response;

import com.project.moviebag.models.Movie;

//single movie
public class MovieResponse {

    //finding the movie object
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
