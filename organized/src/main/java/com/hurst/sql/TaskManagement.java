package com.hurst.sql;

import com.hurst.components.ProgressOption;
import com.hurst.components.StatusOption;
import com.hurst.components.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskManagement {

    private static final Logger logger = LogManager.getLogger(TaskManagement.class);

    public static ArrayList<Task> getAllTasks(String taskOwner) {

        ArrayList<Task> tasks = new ArrayList<>();

        ResultSet resultSetAllTasks = null;
        String sql = "SELECT * FROM tasks WHERE taskOwner = ?";
        try {
            PreparedStatement statementAllTasks = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statementAllTasks.setString(1, taskOwner);
            resultSetAllTasks = statementAllTasks.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }

        try {
            while(resultSetAllTasks.next()) {
                String taskName = resultSetAllTasks.getString(1);
                String taskDescription = resultSetAllTasks.getString(2);
                LocalDateTime startDate;
                String[][] startDateTemp = {
                        resultSetAllTasks.getString(3).substring(0, 10).split("-"),
                        resultSetAllTasks.getString(3).substring(11).split(":")
                };
                startDate = LocalDateTime.of(
                        Integer.parseInt(startDateTemp[0][0]),
                        Integer.parseInt(startDateTemp[0][1]),
                        Integer.parseInt(startDateTemp[0][2]),
                        Integer.parseInt(startDateTemp[1][0]),
                        Integer.parseInt(startDateTemp[1][1]),
                        Integer.parseInt(startDateTemp[1][2])
                );
                LocalDateTime dueDate;
                String[][] dueDateTemp = {
                        resultSetAllTasks.getString(4).substring(0, 10).split("-"),
                        resultSetAllTasks.getString(4).substring(11).split(":")
                };
                dueDate = LocalDateTime.of(
                        Integer.parseInt(dueDateTemp[0][0]),
                        Integer.parseInt(dueDateTemp[0][1]),
                        Integer.parseInt(dueDateTemp[0][2]),
                        Integer.parseInt(dueDateTemp[1][0]),
                        Integer.parseInt(dueDateTemp[1][1]),
                        Integer.parseInt(dueDateTemp[1][2])
                );

                int estimatedCompletionTime;
                estimatedCompletionTime = resultSetAllTasks.getInt(5);

                StatusOption taskStatus = StatusOption.valueOf(resultSetAllTasks.getString(6));

                tasks.add(new Task(taskOwner,
                        taskName,
                        taskDescription,
                        startDate,
                        dueDate,
                        estimatedCompletionTime,
                        taskStatus));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }

        return tasks;
    }

    public static void addTask(Task taskToAdd) {
        String sql = "INSERT INTO " +
                "tasks " +
                "(taskOwner, taskName, taskDescription, startDate, dueDate, estimatedCompletitionTime, taskStatus) " +
                "VALUES " +
                "(?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = SqlCommandRunner.databaseConnection.prepareStatement(sql);
            statement.setString(1, taskToAdd.getTaskOwner());
            statement.setString(2, taskToAdd.getTaskName());
            statement.setString(3, taskToAdd.getTaskDescription());
            statement.setString(4, taskToAdd.getStartDate().toString());
            statement.setString(5, taskToAdd.getDueDate().toString());
            statement.setInt(6, taskToAdd.getEstimatedCompletionTime());
            statement.setString(7, taskToAdd.getTaskStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(2);
        }
    }

}
