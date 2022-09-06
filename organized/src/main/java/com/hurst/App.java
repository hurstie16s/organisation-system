package com.hurst;

import com.hurst.sql.SqlCommandRunner;
import com.hurst.ui.AppWindow;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

/**
 * The type App.
 */
public class App extends Application {

    private static int currentSeason;

    private static final Logger logger = LogManager.getLogger(App.class);
    private static App instance;
    private final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private final int width = (int) screenBounds.getWidth() - 10;
    private final int height = (int) (screenBounds.getWidth() * 0.5);
    private Stage stage;

    private static Properties applicationProperties;

    /**
     * The entry point of application.
     * <p>
     * Exit Codes:
     * 0 = Program successfully terminated
     * 1 = Generic Error
     * 2 = SQLException
     * 3 = Config File Error
     * 4 = Class Not Found Exception
     * 5 = URI Syntax Exception
     * 6 = Null Pointer Exception
     * 7 = No Such Algorithm Exception
     * 8 = Invalid Key Spec Exception
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        logger.info("starting Application");
        launch();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static App getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) {

        instance = this;
        this.stage = stage;
        String logoFile = App.class.getResource("/logos/Organized-logos.jpeg").toExternalForm();
        stage.getIcons().add(new Image(logoFile));

        openApp();
        SqlCommandRunner.setUrl("jdbc:mysql://localhost:3306/organized");
        SqlCommandRunner.setUser("organized");
        SqlCommandRunner.setPassword("organized1!");
        try {
            SqlCommandRunner.openSqlConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
        logger.info("Connection to Database Successful");
    }

    /**
     * Open app.
     */
    public void openApp() {
        logger.info("Opening App Window");

        var appWindow = new AppWindow(stage, width, height);

        stage.show();
    }

    /**
     * Shutdown.
     */
    public void shutdown() {
        logger.info("Shutting Down");
        try {
            SqlCommandRunner.closeSqlConnection();
        } catch (SQLException e) {
            logger.info("Database already closed");
        } catch (NullPointerException e) {
            logger.info("Database not yet initialised");
        }
        System.exit(0);
    }

    public static int getCurrentSeason() {
        return currentSeason;
    }

    private static void getProperties() {
        applicationProperties = new Properties();
        FileInputStream inputStream;
        try {
            //TODO: Fix error : Works in IDE, doesn't work in jar file : Issue with URI hierarchy
            String filename = App.class.getResource("/appData/config.properties").toExternalForm();
            if (filename == null) throw new NullPointerException();
            File file = new File(new URI(filename));
            inputStream = new FileInputStream(file);

            applicationProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(5);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(6);
        }
    }
    private static void setProperty(String key, String value) {
        try {
            applicationProperties.setProperty(key, value);
            //applicationProperties.store(new FileOutputStream(
            //        App.class.getResource("/appData/config.properties").toExternalForm()), null);
            applicationProperties.store(
                    new FileOutputStream("src/main/resources/appData/config.properties"), null);
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(3);
        }
    }

    public static String getVersion() {
        return applicationProperties.getProperty("VERSION");
    }
}