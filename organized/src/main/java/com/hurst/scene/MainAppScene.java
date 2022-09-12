package com.hurst.scene;

import com.hurst.App;
import com.hurst.components.StatusOption;
import com.hurst.components.Task;
import com.hurst.sql.TaskManagement;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Main app scene.
 */
public class MainAppScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(MainAppScene.class);

    private BorderPane mainPane;

    private final String username;

    private ArrayList<Task> tasks;

    /**
     * Instantiates a new Base scene.
     *
     * @param appWindow the app window
     * @param username  the username
     */
    public MainAppScene(AppWindow appWindow, String username) {
        super(appWindow);
        this.username = username;
    }

    @Override
    public void initialise() {

    }

    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());

        root = new AppPane(appWindow.getWidth(), appWindow.getHeight());

        var menuPane = rootSetUp(App.themeProperties.getProperty("AppBackground"));
        root.getChildren().add(menuPane);

        mainPane = new BorderPane();
        menuPane.getChildren().add(mainPane);

        // Left Side Bar
        var mainAppLeftBar = new VBox();
        mainAppLeftBar.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBar"));
        mainAppLeftBar.setMinWidth(appWindow.getWidth() / 5.0);
        mainAppLeftBar.setMaxWidth(appWindow.getWidth() / 5.0);

        Image titleImage = new Image(
                Objects.requireNonNull(
                        MainAppScene.class.getResource(
                                App.themeProperties.getProperty("MenuTitleImage"))).toExternalForm());
        ImageView title = new ImageView(titleImage);
        title.setPreserveRatio(true);
        title.setFitWidth(appWindow.getWidth() / 6.0);
        title.setTranslateX(10);
        title.setTranslateY(10);

        mainAppLeftBar.getChildren().add(title);

        /*
        Calender:
        -Holds events
        -End date for Tasks
         */
        var calenderButton = generateBasicButton("Calender",
                "calenderButton",
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());

        mainAppLeftBar.getChildren().add(calenderButton);

        /*
        Events:
        -Different option to view events
         */
        var eventsButton = generateBasicButton("Events",
                "eventsButton",
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());

        mainAppLeftBar.getChildren().add(eventsButton);

        /*
        Tasks:
        -To do tasks
         */
        var tasksButton = generateBasicButton("Tasks",
                "tasksButton",
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());
        tasksButton.setOnAction(this::showTasks);

        mainAppLeftBar.getChildren().add(tasksButton);

        /*
        Projects:
        -Have tasks within them
        Multi Part
         */
        var projectsButton = generateBasicButton("Projects",
                "projectsButton",
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());

        mainAppLeftBar.getChildren().add(projectsButton);

        // Toggle Light and Dark Theme
        var toggleThemeButton = generateBasicButton(App.themeProperties.getProperty("ToggleButtonText"),
                App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"),
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());
        toggleThemeButton.setOnAction(this::toggleTheme);

        mainAppLeftBar.getChildren().add(toggleThemeButton);

        // Sign Out
        var signOutButton = generateBasicButton("Sign Out",
                App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"),
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());
        signOutButton.setOnAction(this::signOut);

        mainAppLeftBar.getChildren().add(signOutButton);

        // Lock Account
        var lockAccountButton = generateBasicButton("Lock Account",
                App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"),
                mainAppLeftBar.getMinWidth(),
                mainAppLeftBar.getMaxWidth());
        lockAccountButton.setOnAction(this::lockAccount);

        mainAppLeftBar.getChildren().add(lockAccountButton);

        // Account Settings
        var accountSettingsButton = new Button();
        accountSettingsButton.setCursor(Cursor.OPEN_HAND);

        var settingsIcon = new ImageView(
                Objects.requireNonNull(MainAppScene.class.getResource(
                        App.themeProperties.getProperty("SettingsIcon"))).toExternalForm());
        settingsIcon.setPreserveRatio(true);
        var settingsLabel = new Label("Account Settings");
        settingsLabel.getStyleClass().add(App.themeProperties.getProperty("AccountSettingsButtonLabel"));
        settingsLabel.setAlignment(Pos.CENTER_LEFT);

        var accountSettingsButtonGraphic = new HBox(settingsIcon, settingsLabel);
        accountSettingsButtonGraphic.setAlignment(Pos.CENTER_LEFT);

        accountSettingsButton.setGraphic(accountSettingsButtonGraphic);
        accountSettingsButton.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"));
        accountSettingsButton.setAlignment(Pos.CENTER_LEFT);
        accountSettingsButton.setMinWidth(mainAppLeftBar.getMinWidth());
        accountSettingsButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        accountSettingsButton.setPadding(new Insets(10, 0, 10,0));
        accountSettingsButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(accountSettingsButton);

        mainPane.setLeft(mainAppLeftBar);
    }

    private Button generateBasicButton(String text, String style, double minWidth, double maxWidth) {
        var button = new Button(text);
        button.getStyleClass().add(style);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMinWidth(minWidth);
        button.setMaxWidth(maxWidth);
        button.setPadding(new Insets(10, 0, 10,5));
        button.setTranslateY(30);
        button.setCursor(Cursor.OPEN_HAND);

        return button;
    }

    private void showTasks(ActionEvent event){
        tasks = TaskManagement.getAllTasks(this.username);

        var paneCenter = new VBox();
        var scroller = new ScrollPane();
        scroller.setContent(paneCenter);
        scroller.setFitToWidth(true);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainPane.setCenter(scroller);

        for (Task task : tasks) {
            paneCenter.getChildren().add(task.getTaskUIConcise());
        }

        // Add tasks
        var addTaskButton = new Button("Add Task");
        addTaskButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        addTaskButton.setAlignment(Pos.CENTER);
        addTaskButton.setCursor(Cursor.OPEN_HAND);
        addTaskButton.setOnAction(this::addTaskGUI);
        paneCenter.getChildren().add(addTaskButton);
    }

    private void addTaskGUI(ActionEvent event) {

        var addTaskUI = new GridPane();

        var taskNameInput = new TextField();
        taskNameInput.setPromptText("Task Name");
        taskNameInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        taskNameInput.setAlignment(Pos.CENTER);
        taskNameInput.setMinWidth(200);
        taskNameInput.setMaxWidth(300);
        addTaskUI.add(taskNameInput, 0, 0);

        var taskDescriptionInput = new TextArea();
        taskDescriptionInput.setPromptText("Task Description");
        taskDescriptionInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        taskDescriptionInput.setCursor(Cursor.TEXT);
        taskDescriptionInput.setMinWidth(300);
        taskDescriptionInput.setMaxHeight(400);
        taskDescriptionInput.setPrefRowCount(5);
        addTaskUI.add(taskDescriptionInput, 0, 1);

        LocalDate[] startDate = new LocalDate[1];
        var startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");
        startDatePicker.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        startDatePicker.setOnAction(e -> {
            startDate[0] = startDatePicker.getValue();
        });
        addTaskUI.add(startDatePicker, 0, 2);

        var startTimeHour = new ChoiceBox<Integer>();
        for (int i = 0; i < 24; i++) {
            startTimeHour.getItems().add(i);
        }
        addTaskUI.add(startTimeHour, 1, 2);

        LocalDate[] dueDate = new LocalDate[1];
        var dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Start Date");
        dueDatePicker.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        dueDatePicker.setOnAction(e -> {
            dueDate[0] = dueDatePicker.getValue();
        });
        addTaskUI.add(dueDatePicker, 0, 3);

        var dueTimeHour = new ChoiceBox<Integer>();
        for (int i = 0; i < 60; i++) {
            dueTimeHour.getItems().add(i);
        }
        addTaskUI.add(dueTimeHour, 1, 3);

        var estimatedCompletionTimeInput = new TextField();
        estimatedCompletionTimeInput.setPromptText("Number of hours to complete");
        estimatedCompletionTimeInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        estimatedCompletionTimeInput.setAlignment(Pos.CENTER);
        estimatedCompletionTimeInput.setMinWidth(150);
        estimatedCompletionTimeInput.setMaxWidth(200);
        addTaskUI.add(estimatedCompletionTimeInput, 0, 4);

        var taskStatusInput = new ChoiceBox<String>();
        taskStatusInput.getItems().addAll("Not Started",
                "In Progress",
                "Paused");
        addTaskUI.add(taskStatusInput, 0, 5);

        var addTaskButton = new Button("Add Task");
        addTaskButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        addTaskButton.setAlignment(Pos.CENTER);
        addTaskButton.setCursor(Cursor.OPEN_HAND);
        addTaskButton.setOnAction(event1 -> addTask(taskNameInput.getText(),
                taskDescriptionInput.getText(),
                LocalDateTime.of(startDate[0], LocalTime.of(startTimeHour.getValue(), 0)),
                LocalDateTime.of(dueDate[0], LocalTime.of(dueTimeHour.getValue(), 0)),
                Integer.parseInt(estimatedCompletionTimeInput.getText()),
                StatusOption.valueOf(taskStatusInput.getValue())));
        addTaskUI.add(addTaskButton, 0, 6);

        addTaskUI.setVgap(5);
        addTaskUI.setHgap(5);
    }

    private void addTask(String taskName,
                         String taskDescription,
                         LocalDateTime startDateTime,
                         LocalDateTime dueDateTime,
                         Integer estimatedCompletionTime,
                         StatusOption taskStatus) {
        // Create Task Option
        Task newTask = new Task(this.username,
                taskName,
                taskDescription,
                startDateTime,
                dueDateTime,
                estimatedCompletionTime,
                taskStatus);

        // Add Task to Database
        TaskManagement.addTask(newTask);

        // Add Task to ArrayList : May be redundant
        tasks.add(newTask);

        // Reload Tasks UI
        this.showTasks(null);
    }

    private void signOut(ActionEvent event) {
        appWindow.loadScene(new MenuScene(appWindow));
    }

    private void lockAccount(ActionEvent event){
        appWindow.loadScene(new LockScene(appWindow, username));
    }
}
