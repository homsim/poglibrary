package com.poglibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;

public class Setup {
    protected Setup() {
        setup();
    }

    private static void setup() {
        try {
            if (!checkMariaDB()) {
                installMariaDB();
            } else {
                Logger.getLogger("globalLogger").log(Level.INFO, "Installation of MariaDB already found. Skipping installation, but continuing initialization of the database. ");
            }
            createUser();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1396) {
                StringBuilder exStrBld = new StringBuilder();
                exStrBld.append("SQL Error Code: ");
                exStrBld.append(ex.getErrorCode());
                exStrBld.append(". User '");
                exStrBld.append(Queries.DB_USER);
                exStrBld.append("' already exists. Proceeding...");
                Logger.getLogger("globalLogger").log(Level.INFO, exStrBld.toString());
            } else if (ex.getErrorCode() == 1045) {
                StringBuilder exStrBld = new StringBuilder();
                exStrBld.append(ex.getSQLState());
                exStrBld.append("\n");
                exStrBld.append("Could not create MariaDB user '");
                exStrBld.append(Queries.DB_USER);
                exStrBld.append("' for existing installation of MariaDB. ");
                exStrBld.append("Likely, because the root user could not be logged in. ");
                Logger.getLogger("globalLogger").log(Level.SEVERE, exStrBld.toString());        
            }            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger("globalLogger").log(Level.SEVERE, ex.getCause().toString());
        }
    }

    private static boolean checkMariaDB() {
        boolean installExists = false;
        try {
            int exitVal = execBash("mariadb --version");
            if (exitVal == 0) {
                installExists = true;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger("globalLogger").log(Level.SEVERE, ex.getCause().toString());
        }
        return installExists;
    }

    private static void createUser() throws SQLException {
        Connection conn = DriverManager.getConnection(Queries.DB_BASE_URL + ":" + Queries.DB_PORT, "root", "");

        String stmt;
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("CREATE USER ?@'localhost' ");
        stmtBld.append(";");
        stmt = stmtBld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, Queries.DB_USER);

        prepStmt.executeQuery();
    }

    private static void installMariaDB() throws IOException, InterruptedException {
        Logger.getLogger("globalLogger").log(Level.INFO, "Starting installation of MariaDB.");
        execBash("chmod +x ../../../../install.sh"); // not sure if necessary
        execBash("../../../../install.sh");       // to be written !!!
    }

    private static int execBash(String cmd) throws InterruptedException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", cmd);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        StringBuilder output = new StringBuilder();
        String line;
        while ((line =reader.readLine()) != null) {
            output.append(line);
        }
        Logger.getLogger("globalLogger").log(Level.INFO, output.toString());

        return process.waitFor();
    }
}
