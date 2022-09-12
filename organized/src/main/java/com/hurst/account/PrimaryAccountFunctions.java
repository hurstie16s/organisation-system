package com.hurst.account;

import com.hurst.sql.SqlCommandRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Primary account functions.
 */
public class PrimaryAccountFunctions {

    private static final Logger logger = LogManager.getLogger(PrimaryAccountFunctions.class);

    /**
     * Check username boolean.
     *
     * @param usernameToCheck the username to check
     * @return the boolean
     */
    public static boolean checkUsername(String usernameToCheck) {
        String sql = "SELECT username FROM users WHERE username = ?";
        ResultSet resultSet;
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setString(1, usernameToCheck);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (Objects.equals(resultSet.getString("username"), usernameToCheck)) return false; //Username not available
        } catch (SQLException e) {
            return true;
        }
        return false;
    }

    /**
     * Sign in boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public static boolean signIn(String username, String password) {
        if (checkUsername(username)) return false;

        String sql = "SELECT securePassword FROM users WHERE username = ?";
        String storedPassword = null;
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            storedPassword = resultSet.getString("securePassword");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }

        return PasswordSecurity.validatePassword(password, storedPassword);
    }

    /**
     * Sign up boolean.
     *
     * @param firstname            the firstname
     * @param surname              the surname
     * @param username             the username
     * @param password             the password
     * @param passwordConfirmation the password confirmation
     * @return the boolean
     */
    public static int signUp(String firstname,
                                 String surname, 
                                 String username, 
                                 String password, 
                                 String passwordConfirmation) {
        // Check username is available
        if (!checkUsername(username)) return 1;

        // Check password meets requirements
        if (!PasswordSecurity.checkPasswordRequirements(password, passwordConfirmation)) return 2;

        // Generate Secure Password
        String securePassword = PasswordSecurity.securePassword(password);

        // Add user to database
        // SQL
        String sqlSignUp = "INSERT INTO " +
                "users " +
                "(username, firstname, surname, securePassword, workingHours, workingDays) " +
                "VALUES (?,?,?,?, 24, 7)";
        try {
            PreparedStatement statementSignUp = SqlCommandRunner.databaseConnection.prepareStatement(sqlSignUp);
            // Insert values for INSERT statement
            statementSignUp.setString(1, username);
            statementSignUp.setString(2, firstname);
            statementSignUp.setString(3, surname);
            statementSignUp.setString(4, securePassword);
            // Execute INSERT statement
            statementSignUp.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }

        return 0;
    }

    public static void setWorkingHoursDays(String username, int workDays, int workHours) {
        String sql = "UPDATE users SET workingHours = ?, workingDays = ? WHERE username = ?";
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setInt(1, workHours);
            statement.setInt(2, workDays);
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }
    }

    public static int[] getWorkingDaysHours(String username) {
        String sql = "SELECT workingHours, workingDays FROM users WHERE username = ?";
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new int[]{resultSet.getInt(0), resultSet.getInt(1)};
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }
        return new int[0];
    }

}
