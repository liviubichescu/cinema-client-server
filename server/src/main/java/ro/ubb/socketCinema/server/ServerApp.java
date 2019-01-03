package ro.ubb.socketCinema.server;

import ro.ubb.socketCinema.common.Message;
import ro.ubb.socketCinema.common.domain.Movie;
import ro.ubb.socketCinema.server.repository.Repository;
import ro.ubb.socketCinema.server.repository.databaseRepo.MoviesDatabaseRepository;
import ro.ubb.socketCinema.server.serviceServer.ServiceMovieServerImpl;
import ro.ubb.socketCinema.server.tcp.TcpServer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ro.ubb.socketCinema.common.service.ServiceMovieInterface.PORT;


/**
 * author: liviu
 */
public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpServer tcpServer = new TcpServer(executorService, PORT);

        Repository<Long, Movie> repository = new MoviesDatabaseRepository();

        ServiceMovieServerImpl serviceMovieServer = new ServiceMovieServerImpl(repository, executorService, tcpServer);

        tcpServer.allHandler(
                "getAllMovies", request -> {
                    Future<List<Movie>> result = serviceMovieServer.getAllMovies();
                    try {
                        //compute response
                        return new Message(Message.OK, result.get().toString());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.allHandler(
                "removeMovie", request -> {
                    Long id = Long.valueOf(request.getBody());
                    Future<?> result = serviceMovieServer.removeMovie(id);
                    try {
                        return new Message(Message.OK, result.get().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.allHandler(
                "addMovie", request -> {
                    // convertesc stringul primit de la client intr-un film
                    Movie movie = serviceMovieServer.convertToMovie(request.getBody());
                    //salvez filmul in baza de date
                    Future<?> result = serviceMovieServer.addMovie(movie);
                    try {
                        return new Message(Message.OK, result.get().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });

        tcpServer.allHandler(
                "updateMovie", request -> {
                    // convertesc stringul primit de la client intr-un film
                    Movie movie = serviceMovieServer.convertToMovie(request.getBody());
                    //salvez filmul in baza de date
                    Future<?> result = serviceMovieServer.updateMovie(movie);
                    try {
                        return new Message(Message.OK, result.get().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Message(Message.ERROR, e.getMessage());
                    }
                });


        tcpServer.startServer();

        System.out.println("server - bye");
    }
}
