package com.hurst.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * The type Sql command runner.
 */
public abstract class SqlCommandRunner {
    public static Connection databaseConnection;
    private static String url;
    private static String user;
    private static String password;
    private static final Logger logger = LogManager.getLogger(SqlCommandRunner.class);

    public static void setUrl(String url) {
        SqlCommandRunner.url = url;
    }

    public static void setUser(String user) {
        SqlCommandRunner.user = user;
    }

    public static void setPassword(String password) {
        SqlCommandRunner.password = password;
    }

    /**
     * Initialise sql connection.
     *
     * @throws SQLException the sql exception
     */
    public static void openSqlConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.fatal(e.getMessage());
            System.exit(4);
        }
        logger.info("Attempting to connect to Database " + url);
        databaseConnection = DriverManager.getConnection(url, user, password);
        databaseConnection.setSchema("swimRecord");
    }

    public static void closeSqlConnection() throws SQLException {
        databaseConnection.close();
    }
}