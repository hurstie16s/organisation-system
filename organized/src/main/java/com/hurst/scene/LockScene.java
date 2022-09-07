package com.hurst.scene;

import com.hurst.App;
import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LockScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(LockScene.class);

    private final String username;

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
    }
}
