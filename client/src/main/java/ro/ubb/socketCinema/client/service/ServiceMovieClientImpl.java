package ro.ubb.socketCinema.client.service;

import ro.ubb.socketCinema.client.tcp.TcpClient;
import ro.ubb.socketCinema.common.Message;
import ro.ubb.socketCinema.common.domain.Movie;
import ro.ubb.socketCinema.common.service.ServiceMovieInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServiceMovieClientImpl implements ServiceMovieInterface {

    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ServiceMovieClientImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    private List<Movie> convertToMovies(String result) {
        String[] res = result.substring(1, result.length() - 2).split(";, ");
        List<Movie> list = new ArrayList<>();

        for (String s : res) {
            String[] rezultat = s.split(", ");

            String idS = rezultat[0].replace("id= ", "");
            String title = rezultat[1].replace("title= ", "");
            String director = rezultat[2].replace("director= ", "");
            String producer = rezultat[3].replace("producer= ", "");
            String relesedYearS = rezultat[4].replace("relesedyear= ", "");
            String bugetS = rezultat[5].replace("buget= ", "");

            Long id = Long.parseLong(idS);
            Integer relesedYear = Integer.parseInt(relesedYearS);
            Double buget = Double.parseDouble(bugetS);

            Movie movie = new Movie(id, title, director, producer, relesedYear, buget);
            list.add(movie);

        }
        return list;
    }


    @Override
    public Future<List<Movie>> getAllMovies() {
        return executorService.submit(() -> {
            Message request = new Message("getAllMovies", "");
            Message response = tcpClient.sendAndReceive(request);
            String res = response.getBody();

            return convertToMovies(res);
        });
    }

    @Override
    public Future<?> removeMovie(Long movieId) {
        return executorService.submit(() -> {
            Message request = new Message("removeMovie", movieId.toString());
            Message response = tcpClient.sendAndReceive(request);

            return response.getBody();

        });
    }

    @Override
    public Future<?> addMovie(Movie movie) {
        return executorService.submit(() -> {
            Message request = new Message("addMovie", movie.toString());
            Message response = tcpClient.sendAndReceive(request);

            return response.getBody();

        });
    }

    @Override
    public Future<?> updateMovie(Movie movie) {
        return executorService.submit(() -> {
            Message request = new Message("updateMovie", movie.toString());
            Message response = tcpClient.sendAndReceive(request);

            return response.getBody();

        });
    }


}
