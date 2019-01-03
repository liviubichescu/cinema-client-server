package ro.ubb.socketCinema.server.serviceServer;

import ro.ubb.socketCinema.common.domain.Movie;
import ro.ubb.socketCinema.common.service.ServiceMovieInterface;
import ro.ubb.socketCinema.server.tcp.TcpServer;
import ro.ubb.socketCinema.server.repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class ServiceMovieServerImpl implements ServiceMovieInterface {

    private Repository<Long, Movie> repository;
    private ExecutorService executorService;
    private TcpServer tcpServer;

    public ServiceMovieServerImpl(Repository<Long, Movie> repository, ExecutorService executorService, TcpServer tcpServer) {
        this.repository = repository;
        this.executorService = executorService;
        this.tcpServer = tcpServer;
    }

    public Movie convertToMovie(String strMovie) {

        String[] rezultat = strMovie.substring(0, strMovie.length() - 1).split(", ");

        String idS = rezultat[0].replace("id= ", "");
        String title = rezultat[1].replace("title= ", "");
        String director = rezultat[2].replace("director= ", "");
        String producer = rezultat[3].replace("producer= ", "");
        String relesedyearS = rezultat[4].replace("relesedyear= ", "");
        String bugetS = rezultat[5].replace("buget= ", "");

        Long id = Long.parseLong(idS);
        int relesedyear = Integer.parseInt(relesedyearS);
        double buget = Double.parseDouble(bugetS);

        return new Movie(id, title, director, producer, relesedyear, buget);
    }

    @Override
    public Future<List<Movie>> getAllMovies() {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();
            List<Movie> movieList = new ArrayList<>();
            for (Movie movie : movies) {
                movieList.add(movie);
            }

            return movieList;
        });
    }


    @Override
    public Future<?> removeMovie(Long movieId) {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();

            Set<Movie> filteredMovies = new HashSet<>();
            movies.forEach(filteredMovies::add);
            filteredMovies.removeIf(movie -> movie.getId() != movieId);

            if (filteredMovies.size() > 0) {
                repository.delete(movieId);
                return "Movie deleted!!!";
            } else {
                return "Movie id not found!!!";
            }

        });
    }

    @Override
    public Future<?> addMovie(Movie myMovie) {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();

            Set<Movie> filteredMovies = new HashSet<>();
            movies.forEach(filteredMovies::add);
            filteredMovies.removeIf(movie -> movie.getId() != myMovie.getId());

            if (filteredMovies.size() <= 0) {
                repository.save(myMovie);
                return "Movie saved!!!";
            } else {
                return "ID already in database!!!";
            }

        });
    }

    @Override
    public Future<?> updateMovie(Movie myMovie) {
        return executorService.submit(() -> {
            Iterable<Movie> movies = repository.findAll();

            Set<Movie> filteredMovies = new HashSet<>();
            movies.forEach(filteredMovies::add);
            filteredMovies.removeIf(movie -> movie.getId() != myMovie.getId());

            if (filteredMovies.size() > 0) {
//                repository.delete(myMovie.getId());
                repository.update(myMovie);
                return "Movie updated!!!";
            } else {
                return "Movie Id is not in database!!!";
            }

        });
    }

}
