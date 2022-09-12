package com.hurst.components;


import com.hurst.account.PrimaryAccountFunctions;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * The type Task.
 */
public class Task {

    private static final Logger logger = LogManager.getLogger(Task.class);

    private final String taskOwner;

    private final SimpleStringProperty taskName;

    private final SimpleStringProperty taskDescription;

    private final SimpleObjectProperty<LocalDateTime> startDate;

    private final SimpleObjectProperty<LocalDateTime> dueDate;

    private final SimpleIntegerProperty estimatedCompletionTime;

    private final SimpleObjectProperty<LocalDateTime> completionDate;

    private final SimpleObjectProperty<StatusOption> taskStatus;

    private final SimpleObjectProperty<ProgressOption> taskProgress;

    private GridPane taskUIConcise;

    private Button taskNameButton;

    private Text taskDescriptionText;

    private Label startDateLabel;

    private Label dueDateLabel;

    private Label completionDateLabel;

    private Label taskStatusLabel;

    private Label taskProgressLabel;

    /**
     * Instantiates a new Task.
     *
     * @param taskName                the task name
     * @param taskDescription         the task description
     * @param startDate               the start date
     * @param dueDate                 the due date
     * @param estimatedCompletionTime the estimated completion time
     * @param taskStatus              the task status
     */
    public Task(String taskOwner,
                String taskName,
                String taskDescription,
                LocalDateTime startDate,
                LocalDateTime dueDate,
                Integer estimatedCompletionTime,
                StatusOption taskStatus) {
        this.taskOwner = taskOwner;
        this.taskName = new SimpleStringProperty(taskName);
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.estimatedCompletionTime = new SimpleIntegerProperty(estimatedCompletionTime);
        this.completionDate = new SimpleObjectProperty<>(calculateCompletionDate());
        this.taskStatus = new SimpleObjectProperty<>(taskStatus);
        this.taskProgress = new SimpleObjectProperty<>();
        this.taskProgress.bindBidirectional(new SimpleObjectProperty<>(this.getProgress()));

        logger.info("Creating task " + this.taskName);

        createTaskUICConcise();
    }

    // UI
    private void createTaskUICConcise() {
        //Task UI will be a GridPane
        taskUIConcise = new GridPane();
        taskUIConcise.backgroundProperty().bindBidirectional(
                new SimpleObjectProperty<>(
                        new Background(
                                new BackgroundFill(
                                        taskProgress.get().colour,
                                        null,
                                        null))));
        taskUIConcise.setOpacity(.5);
        taskUIConcise.setVgap(5);
        taskUIConcise.setAlignment(Pos.CENTER_LEFT);

        // Task Name
        taskNameButton = new Button();
        taskNameButton.textProperty().bindBidirectional(taskName);
        taskNameButton.getStyleClass().add("taskStandardButtonStyle");
        taskNameButton.setCursor(Cursor.OPEN_HAND);

        // Start Date
        startDateLabel = new Label();
        startDateLabel.textProperty().bindBidirectional(new SimpleStringProperty(startDate.getValue().toString()));
        startDateLabel.getStyleClass().add("taskStandardStyle");

        // Due Date
        dueDateLabel = new Label();
        dueDateLabel.textProperty().bindBidirectional(new SimpleStringProperty(dueDate.getValue().toString()));
        dueDateLabel.getStyleClass().add("taskStandardStyle");

        //Completion Date
        completionDateLabel = new Label();
        completionDateLabel.textProperty().bindBidirectional(new SimpleStringProperty(completionDate.getValue().toString()));
        completionDateLabel.getStyleClass().add("taskStandardStyle");

        // Task Status
        taskStatusLabel = new Label();
        taskStatusLabel.textProperty().bindBidirectional(taskStatus.get().value);
        taskStatusLabel.getStyleClass().add("taskStandardStyle");
        taskStatusLabel.backgroundProperty().bindBidirectional(
                new SimpleObjectProperty<>(
                        new Background(
                                new BackgroundFill(
                                        taskStatus.get().colour,
                                        null,
                                        null))));

        // Task Progress
        taskProgressLabel = new Label();
        taskProgressLabel.textProperty().bindBidirectional(taskProgress.get().value);
        taskProgressLabel.getStyleClass().add("taskStandardStyle");

        // Add Components to container
        taskUIConcise.addRow(0,
                taskNameButton,
                startDateLabel,
                dueDateLabel,
                taskStatusLabel,
                taskProgressLabel);
    }

    // Setters
    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    /**
     * Sets task description.
     *
     * @param taskDescription the task description
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate.set(startDate);
    }

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate.set(dueDate);
    }

    /**
     * Sets estimated completion time.
     *
     * @param estimatedCompletionTime the estimated completion time
     */
    public void setEstimatedCompletionTime(int estimatedCompletionTime) {
        this.estimatedCompletionTime.set(estimatedCompletionTime);
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate.set(completionDate);
    }

    /**
     * Sets task status.
     *
     * @param taskStatus the task status
     */
    public void setTaskStatus(StatusOption taskStatus) {
        if (this.taskStatus.get() == StatusOption.NOT_STARTED && taskStatus == StatusOption.IN_PROGRESS) {
            this.startDate.set(LocalDateTime.now());
        }
        this.taskStatus.set(taskStatus);
    }

    /**
     * Sets task progress.
     *
     * @param taskProgress the task progress
     */
    public void setTaskProgress(ProgressOption taskProgress) {
        this.taskProgress.set(taskProgress);
    }

    // Getters

    public GridPane getTaskUIConcise() {
        return taskUIConcise;
    }

    public ProgressOption getProgress() {

        if (this.completionDate.get().isBefore(this.dueDate.get())) {
            return ProgressOption.AHEAD_SCHEDULE;
        } else if (this.completionDate.get().isEqual(this.dueDate.get())) {
            return ProgressOption.ON_SCHEDULE;
        } else {
            return ProgressOption.BEHIND_SCHEDULE;
        }
    }

    public String getTaskOwner() {
        return this.taskOwner;
    }

    public String getTaskName() {
        return this.taskName.get();
    }

    public String getTaskDescription() {
        return this.taskDescription.get();
    }

    public LocalDateTime getStartDate() {
        return this.startDate.get();
    }

    public LocalDateTime getDueDate() {
        return this.dueDate.get();
    }

    public int getEstimatedCompletionTime() {
        return estimatedCompletionTime.get();
    }

    public StatusOption getTaskStatus() {
        return taskStatus.get();
    }

    // Calculations
    private LocalDateTime calculateCompletionDate() {

        int[] workingHoursDays = PrimaryAccountFunctions.getWorkingDaysHours(this.taskOwner);

        int daysRequired = estimatedCompletionTime.get() / workingHoursDays[0];
        int hoursRequired = estimatedCompletionTime.get() - (daysRequired * workingHoursDays[0]);

        this.completionDate.bind(
                new SimpleObjectProperty<>(this.startDate.get().plusDays(daysRequired).plusHours(hoursRequired)));

        return null;
    }
}
