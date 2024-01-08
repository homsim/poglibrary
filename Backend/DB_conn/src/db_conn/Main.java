package db_conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private final static int port = 1111;

    public static void main(String[] args) {
        String recStr = "";
        try (ServerSocket server = new ServerSocket(port);) {
            while (recStr != "quit") {
                Socket client = server.accept(); // blocks until there is a connection
                recStr = reading(client);
                System.out.println(recStr);
            }

            /*
             * add proper error streamlining,
             * i.e. print certain exception but don't terminate the server socket
             */
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
                 ex.printStackTrace();
        }

    }

    public static String reading(Socket socket) throws IOException {
        BufferedReader bufferReader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        char[] buffer = new char[255];
        int len = bufferReader.read(buffer, 0, 255);
        String mess = new String(buffer, 0, len);

        return mess;
    }
}
