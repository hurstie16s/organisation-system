package com.hurst.scene;

import com.hurst.App;
import com.hurst.account.PrimaryAccountFunctions;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountSetUpScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(AccountSetUpScene.class);

    private final String username;
    /**
     * Instantiates a new Base scene.
     *
     * @param appWindow the app window
     */
    public AccountSetUpScene(AppWindow appWindow, String username) {
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

        // Menu Header
        var menuHeader = new BorderPane();

        Image titleImage = new Image(MenuScene.class.getResource(App.themeProperties.getProperty("MenuTitleImage")).toExternalForm());
        ImageView title = new ImageView(titleImage);
        title.setPreserveRatio(true);
        title.setFitWidth(appWindow.getWidth() / 6.0);
        title.setTranslateX(10);
        title.setTranslateY(10);

        // Toggle Light and Dark Theme
        var toggleThemeButton = new Button(App.themeProperties.getProperty("ToggleButtonText"));
        toggleThemeButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        toggleThemeButton.setOnAction(this::toggleTheme);
        toggleThemeButton.setAlignment(Pos.TOP_RIGHT);
        toggleThemeButton.setCursor(Cursor.OPEN_HAND);

        menuHeader.setLeft(title);
        menuHeader.setRight(toggleThemeButton);

        mainPane.setTop(menuHeader);

        // mainPane Center

        var paneCenter = new GridPane();
        paneCenter.setHgap(5);
        paneCenter.setVgap(5);
        paneCenter.setAlignment(Pos.CENTER);

        var workingHoursHeader = new Label("Working Hours:");
        workingHoursHeader.getStyleClass().add(App.themeProperties.getProperty("Label1"));
        workingHoursHeader.setAlignment(Pos.CENTER);
        paneCenter.add(workingHoursHeader, 0, 0, 3, 1);

        var workStartHour = new ChoiceBox<Integer>();
        for (int i = 0; i < 24; i++) {
            workStartHour.getItems().add(i);
        }
        paneCenter.add(workStartHour, 1, 0, 1, 1);

        var hoursCenter = new Label("to");
        hoursCenter.getStyleClass().add(App.themeProperties.getProperty("Label1"));
        hoursCenter.setAlignment(Pos.CENTER);
        paneCenter.add(hoursCenter, 1, 1, 1, 1);

        var workEndHour = new ChoiceBox<Integer>();
        workStartHour.valueProperty().addListener((obs, oldValue, newValue) -> {
            workEndHour.getItems().removeAll();
            for (int i = newValue + 1; i <= 24; i++ ) {
                workEndHour.getItems().add(i);
            }
        });
        paneCenter.add(workEndHour, 1, 2, 1, 1);

        var breakHoursHeader = new Label("Break Hours");
        breakHoursHeader.getStyleClass().add(App.themeProperties.getProperty("Label1"));
        breakHoursHeader.setAlignment(Pos.CENTER);
        paneCenter.add(breakHoursHeader, 2, 0, 3, 1);

        var breakHourStart = new ChoiceBox<Integer>();
        workStartHour.valueProperty().addListener((obs, oldValue, newValue) -> {
            breakHourStart.getItems().removeAll();
            for (int i = newValue + 1; i <= workEndHour.getValue(); i++) {
                breakHourStart.getItems().add(i);
            }
        });
        workEndHour.valueProperty().addListener((obs, oldValue, newValue) -> {
            breakHourStart.getItems().removeAll();
            for (int i = workStartHour.getValue() + 1; i <= newValue; i++) {
                breakHourStart.getItems().add(i);
            }
        });
        paneCenter.add(breakHourStart, 3, 0, 1, 1);

        var breakCenter = new Label("to");
        breakCenter.getStyleClass().add(App.themeProperties.getProperty("Label1"));
        breakCenter.setAlignment(Pos.CENTER);
        paneCenter.add(breakCenter, 3, 1, 1, 1);

        var breakHourEnd = new ChoiceBox<Integer>();
        breakHourStart.valueProperty().addListener((obs, oldValue, newValue) -> {
            breakHourEnd.getItems().removeAll();
            for (int i = newValue + 1; i <= workEndHour.getValue(); i++) {
                breakHourEnd.getItems().add(i);
            }
        });
        workEndHour.valueProperty().addListener((obs, oldValue, newValue) -> {
            breakHourEnd.getItems().removeAll();
            for (int i = breakHourStart.getValue() + 1; i <= newValue; i++) {
                breakHourEnd.getItems().add(i);
            }
        });
        paneCenter.add(breakHourEnd, 3, 2, 1, 1);

        var workingDaysHeader = new Label("Working Days");
        workingDaysHeader.getStyleClass().add(App.themeProperties.getProperty("Label1"));
        workingDaysHeader.setAlignment(Pos.CENTER);
        paneCenter.add(workingDaysHeader, 4, 0, 3, 1);

        var workMonday = new CheckBox("Monday");
        var workTuesday = new CheckBox("Tuesday");
        var workWednesday = new CheckBox("Wednesday");
        var workThursday = new CheckBox("Thursday");
        var workFriday = new CheckBox("Friday");
        var workSaturday = new CheckBox("Saturday");
        var workSunday = new CheckBox("Sunday");
        paneCenter.addRow(5, workMonday, workTuesday, workWednesday);
        paneCenter.addRow(6, workThursday, workFriday, workSaturday);
        paneCenter.add(workSunday, 7, 1, 1, 1);

        var finishButton = new Button("Finish Account Set Up");
        finishButton.setCursor(Cursor.OPEN_HAND);
        finishButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        finishButton.setOnAction(event -> setUpAccount(workStartHour.getValue(),
                workEndHour.getValue(),
                breakHourStart.getValue(),
                breakHourEnd.getValue(),
                new boolean[]{workMonday.isSelected(),
                        workTuesday.isSelected(),
                        workWednesday.isSelected(),
                        workThursday.isSelected(),
                        workFriday.isSelected(),
                        workSaturday.isSelected(),
                        workSunday.isSelected()}));
        paneCenter.add(finishButton, 8, 0, 3, 1);
        mainPane.setCenter(paneCenter);
    }

    private void setUpAccount(Integer workStartHour,
                              Integer workEndHour,
                              Integer breakStartHour,
                              Integer breakEndHour,
                              boolean[] workDaysSelected) {
        int workDays = 0;
        for (boolean workDay : workDaysSelected) {
            if (workDay) {
                workDays++;
            }
        }
        int workHours = (workEndHour - workStartHour) - (breakEndHour - breakStartHour);

        PrimaryAccountFunctions.setWorkingHoursDays(this.username, workDays, workHours);

        appWindow.loadScene(new MainAppScene(appWindow, this.username));
    }
}
