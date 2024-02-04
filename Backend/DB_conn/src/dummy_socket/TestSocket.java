package dummy_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TestSocket {
    public static void main(String[] args) {
        try {
            String ip = "localhost";
            int port = 1111;
            //String mess = "recBooks&isbn,author,title,borrowed_by";
            String mess = "addBookFromIsbn&6156165";
            Socket socket = new Socket(ip, port);
            socket.setSoTimeout(5000); // timeout after 5 sec
            writeMess(socket, mess);
            String response = readMess(socket);
            System.out.println(response);

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

    private static String readMess(Socket socket) throws IOException {
        final int MAX_BUFFER_LENGTH = 20000; // the buffer length needs to be quite large for the JSONs
        BufferedReader bufferReader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));

        char[] buffer = new char[MAX_BUFFER_LENGTH];
        int len = bufferReader.read(buffer, 0, MAX_BUFFER_LENGTH);
        String mess = new String(buffer, 0, len);

        return mess;
    }
}
