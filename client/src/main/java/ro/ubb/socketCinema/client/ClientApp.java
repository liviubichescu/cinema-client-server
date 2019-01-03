package ro.ubb.socketCinema.client;

import ro.ubb.socketCinema.client.service.ServiceMovieClientImpl;
import ro.ubb.socketCinema.client.tcp.TcpClient;
import ro.ubb.socketCinema.client.ui.ClientConsole;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ro.ubb.socketCinema.common.service.ServiceMovieInterface.LOCALHOST;
import static ro.ubb.socketCinema.common.service.ServiceMovieInterface.PORT;

/**
 * author: liviu
 */
public class ClientApp {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        TcpClient tcpClient = new TcpClient(LOCALHOST, PORT);

        ServiceMovieClientImpl serviceMovieClient = new ServiceMovieClientImpl(executorService, tcpClient);

        ClientConsole clientConsole = new ClientConsole(serviceMovieClient);

        clientConsole.meniu();

        executorService.shutdownNow();
    }
}
