package ua.com.univerpulse;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by svivanov on 08.03.16.
 */
public class TinyHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8000);
        System.out.println("Server started and ready for request.");
        while (true) {
            new ServerConnection(ss.accept()).start();
        }
    }
}
