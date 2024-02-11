package com.poglibrary;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {
        try {
            new Setup();
            Logger.getLogger("globalLogger").log(Level.INFO, "Staring server.");
            Server server = new Server();
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
