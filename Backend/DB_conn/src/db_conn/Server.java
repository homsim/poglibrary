package db_conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 1111;
    //private final static int TIMEOUT = 10000; // timeout in ms

    public Server()
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ServerSocket server = new ServerSocket(PORT);
        while (!server.isClosed()) {
            Socket client = server.accept();
            Interpreter.interpret(reading(client));
        }
        server.close();
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
