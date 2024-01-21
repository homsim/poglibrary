package db_conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 1111;

    public Server()
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ServerSocket server = new ServerSocket(PORT);
        while (!server.isClosed()) {
            Socket client = server.accept();
            Object returnObject = Interpreter.interpret(reading(client));
            sending(client, returnObject.toString());
        }
        server.close();
    }

    private static String reading(Socket socket) throws IOException {
        BufferedReader bufferReader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        char[] buffer = new char[255];
        int len = bufferReader.read(buffer, 0, 255);
        String mess = new String(buffer, 0, len);

        return mess;
    }

    private static void sending(Socket socket, String mess) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(mess);
    }
}
