package com.poglibrary;

import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.Level;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static String logProps = "db_conn/src/main/java/logs.properties";

    public static void main(String[] args) {
        try {
            initLogger();
            new Setup();
            Logger.getLogger("globalLogger").log(Level.INFO, "Staring server.");
            Server server = new Server();
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void initLogger() {
        try {
            FileInputStream logConfigFile = new FileInputStream(logProps);
            LogManager.getLogManager().readConfiguration(logConfigFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
