package db_conn;

import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.Level;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            FileInputStream logConfigFile = new FileInputStream("src/db_conn/logs.properties");
            LogManager.getLogManager().readConfiguration(logConfigFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // not sure if this is necessary...
        Logger globalLogger = Logger.getLogger("globalLogger");

        // start server
        try {
            globalLogger.log(Level.INFO, "Staring server.");
            Server server = new Server();
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
