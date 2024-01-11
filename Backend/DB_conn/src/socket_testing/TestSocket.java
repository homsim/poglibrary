package socket_testing;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TestSocket {
    public static void main(String[] args) {
        try {
            String ip = "localhost";
            int port = 1111;
            String mess = "q";
            Socket socket = new Socket(ip, port);
            writeMess(socket, mess);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void writeMess(Socket socket, String mess) throws IOException {
        PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        printWriter.println(mess);
        printWriter.flush();
    }
}
