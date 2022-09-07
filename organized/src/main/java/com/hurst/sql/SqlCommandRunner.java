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
    /**
     * The constant databaseConnection.
     */
    public static Connection databaseConnection;
    private static String url;
    private static String user;
    private static String password;
    private static final Logger logger = LogManager.getLogger(SqlCommandRunner.class);

    /**
     * Sets url.
     *
     * @param url the url
     */
    public static void setUrl(String url) {
        SqlCommandRunner.url = url;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public static void setUser(String user) {
        SqlCommandRunner.user = user;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
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

    /**
     * Close sql connection.
     *
     * @throws SQLException the sql exception
     */
    public static void closeSqlConnection() throws SQLException {
        databaseConnection.close();
    }
}