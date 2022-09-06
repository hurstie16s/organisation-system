package com.hurst.account;

import com.hurst.sql.SqlCommandRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PrimaryAccountFunctions {

    private static final Logger logger = LogManager.getLogger(PrimaryAccountFunctions.class);

    public static boolean checkUsername(String usernameToCheck) {
        String sql = "SELECT username FROM users WHERE username = ?";
        ResultSet resultSet;
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setString(1, usernameToCheck);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (Objects.equals(resultSet.getString("username"), usernameToCheck)) return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
