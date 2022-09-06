package com.hurst.ui;

import com.hurst.App;
import com.hurst.scene.BaseScene;
import com.hurst.scene.MenuScene;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The type App window.
 */
public class AppWindow {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);

    private final int width;
    private final int height;
    private final Stage stage;
    private BaseScene currentScene;
    private Scene scene;

    /**
     * Instantiates a new App window.
     *
     * @param stage  the stage
     * @param width  the width
     * @param height the height
     */
    public AppWindow(Stage stage, int width, int height) {
        this.width = width;
        this.height = height;

        this.stage = stage;

        setUpStage();

        setUpResources();

        setUpDefaultScene();

        startMenu();
    }

    /**
     * Gets scene.
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    private void setUpResources() {
        Font.loadFont(getClass().getResourceAsStream("/style/Orbitron-Regular.ttf"), 32);
        Font.loadFont(getClass().getResourceAsStream("/style/Orbitron-Bold.ttf"), 32);
        Font.loadFont(getClass().getResourceAsStream("/style/Orbitron-ExtraBold.ttf"), 32);
    }

    /**
     * Sets up stage.
     */
    public void setUpStage() {
        stage.setTitle("Organized");
        stage.setMinWidth(width);
        stage.setMinHeight(height + 20);
        stage.setOnCloseRequest(event -> App.getInstance().shutdown());
    }

    /**
     * Load scene.
     *
     * @param newScene the new scene
     */
    public void loadScene(BaseScene newScene) {

        // Create and set up new scene
        newScene.build();
        currentScene = newScene;
        scene = newScene.setScene();
        stage.setScene(scene);

        // Initialise the scene when it is ready
        Platform.runLater(() -> currentScene.initialise());
    }

    /**
     * Sets up default scene.
     */
    public void setUpDefaultScene() {
        this.scene = new Scene(new Pane(), width, height, Color.BLACK);
        stage.setScene(this.scene);
    }

    /**
     * Start menu.
     */
    public void startMenu() {
        loadScene(new MenuScene(this));
    }
}
