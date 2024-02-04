package db_conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    private volatile boolean isStopped;
    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1111);
            // serverSocket.setSoTimeout(0); // ?
        } catch (IOException ex) {
            throw new RuntimeException("Unable to open ServerSocket: " + ex.getMessage());
        }

        // Run server socket handler in separate thread so accept doesn't block stop
        Thread serverSocketHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket clientSocket;
                while (!isStopped) {
                    try {
                        System.out.println("Waiting for connections...");
                        clientSocket = serverSocket.accept();
                        threadPool.execute(new ClientHandler(clientSocket));
                    } catch (IOException ex) {
                        errorLog(ex.getMessage());
                    }
                }
            }
        });

        serverSocketHandler.start();
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;

        @Override
        public void run() {
            printHandlerStartupMess(clientSocket);
            try {
                String clientMess = reading(clientSocket);
                Object returnObj = Interpreter.interpret(clientMess);
                sending(clientSocket, returnObj.toString());

                clientSocket.close();
            } catch (Exception ex) {
                // ex.printStackTrace();
                Logger.getLogger("globalLogger").log(Level.WARNING, ex.getCause().toString());
                try {
                    sending(clientSocket, ex.getCause().toString());
                } catch (IOException exIO) {
                    System.err.println(ex.getClass());
                }
            }
        }

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        private static void printHandlerStartupMess(Socket clientSocket) {
            StringBuilder strBld = new StringBuilder("Handling client: ");
            strBld.append(clientSocket.toString());
            System.out.println(strBld.toString());
        }
    }

    public synchronized void stop() throws IOException {
        System.out.println("Stopping server...");
        isStopped = true;
        if (serverSocket != null)
            serverSocket.close();
        threadPool.shutdown();
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

    private void errorLog(String message) {
        System.err.println("ERROR: " + message);
    }

}