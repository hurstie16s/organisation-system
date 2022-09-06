package com.hurst.scene;

import com.hurst.ui.AppPane;
import com.hurst.ui.AppWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuScene extends BaseScene{

    private static final Logger logger = LogManager.getLogger(MenuScene.class);

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

        var menuPane = rootSetUp("app-background");
        root.getChildren().add(menuPane);
    }
}
