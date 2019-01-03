package ro.ubb.socketCinema.common.service;

import ro.ubb.socketCinema.common.domain.Movie;

import java.util.List;
import java.util.concurrent.Future;

public interface ServiceMovieInterface {

    String LOCALHOST ="localhost";
    int PORT = 12345;

    Future<?> addMovie(Movie movie);

    Future<List<Movie>> getAllMovies();

    Future<?> removeMovie(Long movieId);

    Future<?> updateMovie(Movie movie);


}
