package ro.ubb.socketCinema.server.tcp;

import ro.ubb.socketCinema.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

/**
 * author: liviu
 */
public class TcpServer {
    private ExecutorService executorService;
    private int port;

    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService, int port) {
        this.executorService = executorService;
        this.port = port;

        methodHandlers = new HashMap<>();
    }

    public void allHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

//    public void removeHandler(String methodName, UnaryOperator<Message> handler) {
//        methodHandlers.put(methodName, handler);
//    }
//
//    public void saveHandler(String methodName, UnaryOperator<Message> handler) {
//        methodHandlers.put(methodName, handler);
//    }
//
//    public void updateHandler(String methodName, UnaryOperator<Message> handler) {
//        methodHandlers.put(methodName, handler);
//    }


    public void startServer() {
        System.out.println("starting server");
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("could not start server", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (var is = clientSocket.getInputStream();
                 var os = clientSocket.getOutputStream()) {

                Message request = new Message();
                request.readFrom(is);
                System.out.println("server - received request: " + request);

                Message response = methodHandlers.get(request.getHeader()).apply(request);

                System.out.println("server - computed response: " + response);
                response.writeTo(os);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("server - data exchange error", e);
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
