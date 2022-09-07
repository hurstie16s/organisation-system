package com.hurst.scene;

import com.hurst.App;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Main app scene.
 */
public class MainAppScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(MainAppScene.class);

    private final String username;

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

        var mainPane = new BorderPane();
        menuPane.getChildren().add(mainPane);

        // Left Side Bar
        var mainAppLeftBar = new VBox();
        mainAppLeftBar.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBar"));
        mainAppLeftBar.setMinWidth(appWindow.getWidth() / 5);
        mainAppLeftBar.setMaxWidth(appWindow.getWidth() / 5);

        Image titleImage = new Image(
                MainAppScene.class.getResource(App.themeProperties.getProperty("MenuTitleImage")).toExternalForm());
        ImageView title = new ImageView(titleImage);
        title.setPreserveRatio(true);
        title.setFitWidth(appWindow.getWidth() / 6);
        title.setTranslateX(10);
        title.setTranslateY(10);

        mainAppLeftBar.getChildren().add(title);

        /*
        Calender:
        -Holds events
        -End date for Tasks
         */
        var calenderButton = new Button("Calender");
        calenderButton.getStyleClass().add("calenderButton");
        calenderButton.setMinWidth(mainAppLeftBar.getMinWidth());
        calenderButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        calenderButton.setAlignment(Pos.CENTER_LEFT);
        calenderButton.setPadding(new Insets(10, 0, 10,0));
        calenderButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(calenderButton);

        /*
        Events:
        -Different option to view events
         */
        var eventsButton = new Button("Events");
        eventsButton.getStyleClass().add("eventsButton");
        eventsButton.setMinWidth(mainAppLeftBar.getMinWidth());
        eventsButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        eventsButton.setAlignment(Pos.CENTER_LEFT);
        eventsButton.setPadding(new Insets(10, 0, 10,0));
        eventsButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(eventsButton);

        /*
        Tasks:
        -To do tasks
         */
        var tasksButton = new Button("Tasks");
        tasksButton.getStyleClass().add("tasksButton");
        tasksButton.setMinWidth(mainAppLeftBar.getMinWidth());
        tasksButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        tasksButton.setAlignment(Pos.CENTER_LEFT);
        tasksButton.setPadding(new Insets(10, 0, 10,0));
        tasksButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(tasksButton);

        /*
        Projects:
        -Have tasks within them
        Multi Part
         */
        var projectsButton = new Button("Projects");
        projectsButton.getStyleClass().add("projectsButton");
        projectsButton.setMinWidth(mainAppLeftBar.getMinWidth());
        projectsButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        projectsButton.setAlignment(Pos.CENTER_LEFT);
        projectsButton.setPadding(new Insets(10, 0, 10,0));
        projectsButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(projectsButton);

        // Toggle Light and Dark Theme
        var toggleThemeButton = new Button(App.themeProperties.getProperty("ToggleButtonText"));
        toggleThemeButton.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"));
        toggleThemeButton.setOnAction(this::toggleTheme);
        toggleThemeButton.setAlignment(Pos.CENTER_LEFT);
        toggleThemeButton.setMinWidth(mainAppLeftBar.getMinWidth());
        toggleThemeButton.setMaxHeight(mainAppLeftBar.getMaxHeight());
        toggleThemeButton.setPadding(new Insets(10, 0, 10,0));
        toggleThemeButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(toggleThemeButton);

        // Sign Out
        var signOutButton = new Button("Sign Out");
        signOutButton.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"));
        signOutButton.setAlignment(Pos.CENTER_LEFT);
        signOutButton.setMinWidth(mainAppLeftBar.getMinWidth());
        signOutButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        signOutButton.setPadding(new Insets(10, 0, 10,0));
        signOutButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(signOutButton);

        // Lock Account
        var lockAccountButton = new Button("Lock Account");
        lockAccountButton.getStyleClass().add(App.themeProperties.getProperty("MainAppSideBarButtonThemeToggle"));
        lockAccountButton.setAlignment(Pos.CENTER_LEFT);
        lockAccountButton.setMinWidth(mainAppLeftBar.getMinWidth());
        lockAccountButton.setMaxWidth(mainAppLeftBar.getMaxWidth());
        lockAccountButton.setPadding(new Insets(10, 0, 10,0));
        lockAccountButton.setTranslateY(30);

        mainAppLeftBar.getChildren().add(lockAccountButton);

        // Account Settings
        var accountSettingsButton = new Button();
        var settingsIcon = new ImageView(
                MainAppScene.class.getResource(
                        App.themeProperties.getProperty("SettingsIcon")).toExternalForm());
        settingsIcon.setPreserveRatio(true);
        var settingsLabel = new Label("Account Settings");
        settingsLabel.getStyleClass().add(App.themeProperties.getProperty("AccountSettingsButtonLabel"));
        settingsLabel.setAlignment(Pos.CENTER_LEFT);

        var accountSettingsButtonGraphic = new HBox(settingsIcon, settingsLabel);

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

    private void toggleTheme(ActionEvent event) {
        App.toggleTheme(MainAppScene.this);
    }
}
