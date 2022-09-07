package com.hurst.scene;

import com.hurst.App;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * The type Base scene.
 */
public abstract class BaseScene {

    /**
     * The App window.
     */
    protected final AppWindow appWindow;

    /**
     * The Root.
     */
    protected AppPane root;

    /**
     * The Scene.
     */
    protected Scene scene;

    /**
     * Instantiates a new Base scene.
     *
     * @param appWindow the app window
     */
    public BaseScene(AppWindow appWindow) {
        this.appWindow = appWindow;
    }

    /**
     * Initialise.
     */
    public abstract void initialise();

    /**
     * Build.
     */
    public abstract void build();

    /**
     * Sets scene.
     *
     * @return the scene
     */
    public Scene setScene() {
        var previous = appWindow.getScene();
        Scene scene = new Scene(root, previous.getWidth(), previous.getHeight(), Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
        this.scene = scene;
        return scene;
    }

    /**
     * Gets scene.
     *
     * @return the scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Root set up stack pane.
     *
     * @param cssURL the css url
     * @return the stack pane
     */
    public StackPane rootSetUp(String cssURL) {

        var pane = new StackPane();
        pane.setMaxWidth(appWindow.getWidth());
        pane.setMaxHeight(appWindow.getHeight());
        pane.getStyleClass().add(cssURL);
        return pane;
    }

    protected void toggleTheme(ActionEvent event) {
        App.toggleTheme(BaseScene.this);
    }
}
