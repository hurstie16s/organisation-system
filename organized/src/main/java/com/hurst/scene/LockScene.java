package com.hurst.scene;

import com.hurst.App;
import com.hurst.account.PrimaryAccountFunctions;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LockScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(LockScene.class);

    private final String username;

    private VBox uiComponentsUnlock;

    /**
     * Instantiates a new Base scene.
     *
     * @param appWindow the app window
     */
    public LockScene(AppWindow appWindow, String username) {
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
        title.setFitWidth(appWindow.getWidth() / 6);
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

        uiComponentsUnlock = new VBox();

        // Password Input
        var passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setAlignment(Pos.CENTER);
        passwordInput.setMaxWidth(200);
        passwordInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));

        // Sign In
        var signInButton = new Button("Sign In");
        signInButton.disableProperty().bind((Bindings.isEmpty(passwordInput.textProperty())));
        signInButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        signInButton.setOnAction(event -> signIn(passwordInput.getText()));
        signInButton.setCursor(Cursor.OPEN_HAND);

        uiComponentsUnlock.getChildren().addAll(passwordInput, signInButton);
        uiComponentsUnlock.setAlignment(Pos.CENTER);
        uiComponentsUnlock.setSpacing(5);

        mainPane.setCenter(uiComponentsUnlock);
    }

    private void signIn(String password) {
        boolean signInAllowed = PrimaryAccountFunctions.signIn(username, password);

        if(signInAllowed) {
            // Load MainAppScene
            appWindow.loadScene(new MainAppScene(appWindow, username));
        } else {
            // Inform User that either password or username are incorrect
            if(uiComponentsUnlock.getChildren().size()<3) {
                Label signInRejectionLabel = new Label("Password Incorrect");
                signInRejectionLabel.getStyleClass().add("rejectedLabel");
                uiComponentsUnlock.getChildren().add(signInRejectionLabel);
            }
        }

    }
}
