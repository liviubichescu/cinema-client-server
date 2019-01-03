package ro.ubb.socketCinema.client.tcp;

import ro.ubb.socketCinema.common.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * author: liviu
 */
public class TcpClient {
    private String host;
    private int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(host, port);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()) {

            request.writeTo(os);
//            System.out.println("client - sent request: " + request);

            Message response = new Message();
            response.readFrom(is);
//            System.out.println("client - received response: " + response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("client - exception connecting to" +
                    " server", e);
        }
    }
}
