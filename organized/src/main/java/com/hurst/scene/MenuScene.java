package com.hurst.scene;

import com.hurst.App;
import com.hurst.account.PasswordSecurity;
import com.hurst.account.PrimaryAccountFunctions;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The type Menu scene.
 */
public class MenuScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(MenuScene.class);

    private BorderPane loginFields;

    private VBox uiComponentsSignIn;

    private VBox uiComponentsSignUp;

    private TextField usernameInputSignUp;

    private PasswordField passwordInputSignUp;

    private PasswordField passwordInputSignUpConfirm;

    /**
     * Instantiates a new Menu scene.
     *
     * @param appWindow the app window
     */
    public MenuScene(AppWindow appWindow) {
        super(appWindow);
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

        // SignIn / SignUp GUI
        loginFields = new BorderPane();

        // Set Default option
        loginFields.setCenter(signInGUI());

        // Add options to mainPane
        mainPane.setCenter(loginFields);
    }

    private VBox signInGUI() {

        //TODO: Add Styling

        // Container for sign in ui
        uiComponentsSignIn = new VBox();

        // Username input
        var usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        usernameInput.setAlignment(Pos.CENTER);
        usernameInput.setMaxWidth(200);
        usernameInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));

        // Password Input
        var passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setAlignment(Pos.CENTER);
        passwordInput.setMaxWidth(200);
        passwordInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));

        var signInButton = new Button("Sign In");
        signInButton.disableProperty().bind(
                Bindings.isEmpty(usernameInput.textProperty())
                        .or(Bindings.isEmpty(passwordInput.textProperty())));
        signInButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        signInButton.setOnAction(event -> signIn(usernameInput.getText(), passwordInput.getText()));
        signInButton.setCursor(Cursor.OPEN_HAND);

        var toggleButton = new Button("Sign Up Instead");
        toggleButton.setOnAction((ActionEvent event) -> toggleSignInSignUp(false));
        toggleButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        toggleButton.setCursor(Cursor.OPEN_HAND);

        uiComponentsSignIn.getChildren().addAll(usernameInput, passwordInput, signInButton, toggleButton);
        uiComponentsSignIn.setAlignment(Pos.CENTER);
        uiComponentsSignIn.setSpacing(5);

        return uiComponentsSignIn;
    }

    private GridPane signUpGUI() {

        var uiComponentsSuper = new GridPane();

        // Container for sign in ui
        uiComponentsSignUp = new VBox();

        // Firstname input
        var firstnameInput = new TextField();
        firstnameInput.setPromptText("Firstname");
        firstnameInput.setAlignment(Pos.CENTER);
        firstnameInput.setMaxWidth(200);
        firstnameInput.setMinWidth(200);
        firstnameInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));

        //Surname input
        var surnameInput = new TextField();
        surnameInput.setPromptText("Surname");
        surnameInput.setAlignment(Pos.CENTER);
        surnameInput.setMaxWidth(200);
        surnameInput.setMinWidth(200);
        surnameInput.getStyleClass().add(App.themeProperties.getProperty("TextField1"));

        // Username input
        usernameInputSignUp = new TextField();
        usernameInputSignUp.setPromptText("Username");
        usernameInputSignUp.setAlignment(Pos.CENTER);
        usernameInputSignUp.setMaxWidth(200);
        usernameInputSignUp.setMinWidth(200);
        usernameInputSignUp.setOnAction((ActionEvent event) -> checkUsername(usernameInputSignUp.getText()));
        usernameInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        usernameInputSignUp.setOnKeyTyped(keyEvent -> checkUsername(usernameInputSignUp.getText()));

        // Password Input
        passwordInputSignUp = new PasswordField();
        passwordInputSignUp.setPromptText("Password");
        passwordInputSignUp.setAlignment(Pos.CENTER);
        passwordInputSignUp.setMaxWidth(200);
        passwordInputSignUp.setMinWidth(200);
        passwordInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        //passwordInputSignUp.setOnKeyTyped(keyEvent -> checkPasswordRequirements(passwordInputSignUp.getText(), passwordInputSignUpConfirm.getText()));

        // Password Confirmation
        passwordInputSignUpConfirm = new PasswordField();
        passwordInputSignUpConfirm.setPromptText("Confirm Password");
        passwordInputSignUpConfirm.setAlignment(Pos.CENTER);
        passwordInputSignUpConfirm.setMaxWidth(200);
        passwordInputSignUpConfirm.setMinWidth(200);
        passwordInputSignUpConfirm.getStyleClass().add(App.themeProperties.getProperty("TextField1"));
        //passwordInputSignUpConfirm.setOnKeyTyped(keyEvent -> checkPasswordRequirements(passwordInputSignUp.getText(), passwordInputSignUpConfirm.getText()));

        var signUpButton = new Button("Sign Up");
        signUpButton.disableProperty().bind(
                Bindings.isEmpty(usernameInputSignUp.textProperty())
                        .or(Bindings.isEmpty(passwordInputSignUp.textProperty())));
        signUpButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        signUpButton.setOnAction(event -> signUp(usernameInputSignUp.getText(),
                firstnameInput.getText(),
                surnameInput.getText(),
                passwordInputSignUp.getText(),
                passwordInputSignUpConfirm.getText()));
        signUpButton.setCursor(Cursor.OPEN_HAND);

        var toggleButton = new Button("Sign In Instead");
        toggleButton.setOnAction((ActionEvent event) -> toggleSignInSignUp(true));
        toggleButton.getStyleClass().add(App.themeProperties.getProperty("Button1"));
        toggleButton.setCursor(Cursor.OPEN_HAND);

        uiComponentsSignUp.getChildren().addAll(firstnameInput,
                surnameInput,
                usernameInputSignUp,
                passwordInputSignUp,
                passwordInputSignUpConfirm,
                signUpButton,
                toggleButton);
        uiComponentsSignUp.setAlignment(Pos.CENTER);
        uiComponentsSignUp.setSpacing(5);

        // Password Requirements
        var passwordRequirements = new Label("""
                Password Requirements:
                -Passwords must be between 10 and 20 characters long.
                -Passwords must contain at least 1 lowercase character, a-z.
                -Passwords must contain at least 1 uppercase character, A-Z.
                -Passwords must contains at least 1 digit, 0-9""");
        passwordRequirements.getStyleClass().add(App.themeProperties.getProperty("PasswordRequirementsStyle"));
        passwordRequirements.setAlignment(Pos.CENTER);
        passwordRequirements.setPadding(new Insets(5));

        uiComponentsSuper.addRow(0, uiComponentsSignUp, passwordRequirements);
        uiComponentsSuper.setAlignment(Pos.CENTER);
        uiComponentsSuper.setVgap(5);
        uiComponentsSuper.setHgap(10);

        return uiComponentsSuper;
    }

    private void toggleSignInSignUp (boolean signUp) {
        if (signUp) {
            loginFields.setCenter(signInGUI());
            loginFields.setRight(null);
        } else {
            loginFields.setCenter(signUpGUI());
        }
    }
    private void checkUsername(String usernameToCheck) {
        if(PrimaryAccountFunctions.checkUsername(usernameToCheck)) {
            usernameInputSignUp.getStyleClass().remove(App.themeProperties.getProperty("TextField1Rejected"));
            usernameInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1Accepted"));
        } else {
            usernameInputSignUp.getStyleClass().remove(App.themeProperties.getProperty("TextField1Accepted"));
            usernameInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1Rejected"));
        }

    }

    private void checkPasswordRequirements(String passwordToCheck, String passwordConfirmation) {
        boolean passwordAcceptable = PasswordSecurity.checkPasswordRequirements(passwordToCheck, passwordConfirmation);
        if (passwordAcceptable) {
            //TODO : Work out why border change is not happening
            passwordInputSignUp.getStyleClass().remove(App.themeProperties.getProperty("TextField1Rejected"));
            passwordInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1Accepted"));

            passwordInputSignUpConfirm.getStyleClass().remove(App.themeProperties.getProperty("TextField1Rejected"));
            passwordInputSignUpConfirm.getStyleClass().add(App.themeProperties.getProperty("TextField1Accepted"));
        } else {
            passwordInputSignUp.getStyleClass().remove(App.themeProperties.getProperty("TextField1Accepted"));
            passwordInputSignUp.getStyleClass().add(App.themeProperties.getProperty("TextField1Rejected"));

            passwordInputSignUpConfirm.getStyleClass().remove(App.themeProperties.getProperty("TextField1Accepted"));
            passwordInputSignUpConfirm.getStyleClass().add(App.themeProperties.getProperty("TextField1Rejected"));
        }
    }

    private void signIn(String username, String securePassword) {
        boolean signInAllowed = PrimaryAccountFunctions.signIn(username, securePassword);

        if(signInAllowed) {
            // Load MainAppScene
            appWindow.loadScene(new MainAppScene(appWindow, username));
        } else {
            // Inform User that either password or username are incorrect
            if(uiComponentsSignIn.getChildren().size()<5) {
                Label signInRejectionLabel = new Label("Username or Password Incorrect");
                signInRejectionLabel.getStyleClass().add("rejectedLabel");
                uiComponentsSignIn.getChildren().add(signInRejectionLabel);
            }
        }
    }

    private void signUp(String username,
                        String firstname,
                        String surname,
                        String password,
                        String passwordConfirmation) {
        int signUpAllowed = PrimaryAccountFunctions.signUp(firstname,
                surname,
                username,
                password,
                passwordConfirmation);
        if(signUpAllowed == 0) {
            //appWindow.loadScene(new MainAppScene(appWindow, username));
            appWindow.loadScene(new AccountSetUpScene(appWindow, username));
        } else if(signUpAllowed == 1) {
            // Inform User that username is not available
            Label usernameNotAvailableLabel = new Label("Username not Available");
            usernameNotAvailableLabel.getStyleClass().add("rejectedLabel");
            if (uiComponentsSignUp.getChildren().size()>=8) {
                uiComponentsSignUp.getChildren().remove(7);
            }
            uiComponentsSignUp.getChildren().add(usernameNotAvailableLabel);
        } else {
            // Inform User that password does not meet requirements
            Label passwordRequirementsNotMet = new Label("Password Requirements Not Met");
            passwordRequirementsNotMet.getStyleClass().add("rejectedLabel");
            if (uiComponentsSignUp.getChildren().size()>=8) {
                uiComponentsSignUp.getChildren().remove(7);
            }
            uiComponentsSignUp.getChildren().add(passwordRequirementsNotMet);
        }
    }
}