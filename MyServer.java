package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyServer {

    private int port;
    private ClientHandler clientHandler;
    private final AtomicBoolean stop;

    public MyServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        this.stop = new AtomicBoolean(false);
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try (ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(1000);

            while (!stop.get()) {
                try {
                    Socket client = server.accept();
                    try {
                        clientHandler.handleClient(client.getInputStream(), client.getOutputStream());
                    } catch (IOException e) {
                        System.err.println("Error handling client: " + e.getMessage());
                    } finally {
                        client.close();
                    }
                } catch (SocketTimeoutException e) {
                } catch (IOException e) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server socket error: " + e.getMessage());
        }
    }

    public void close() {
        stop.set(true);
    }
}
