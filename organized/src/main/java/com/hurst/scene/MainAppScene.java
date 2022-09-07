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

import java.util.Objects;

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

        return button;
    }

    private void signOut(ActionEvent event) {
        appWindow.loadScene(new MenuScene(appWindow));
    }

    private void lockAccount(ActionEvent event){
        appWindow.loadScene(new LockScene(appWindow, username));
    }
}
