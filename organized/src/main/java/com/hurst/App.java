package com.hurst;

import com.hurst.scene.BaseScene;
import com.hurst.sql.SqlCommandRunner;
import com.hurst.ui.AppWindow;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * The type App.
 */
public class App extends Application {

    private static final Logger logger = LogManager.getLogger(App.class);
    private static App instance;
    private final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private final int width = (int) screenBounds.getWidth() - 10;
    private final int height = (int) (screenBounds.getWidth() * 0.5);
    private Stage stage;
    private static Properties applicationProperties;
    public static Properties themeProperties;

    private static AppWindow appWindow;

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

        applicationProperties = getProperties("/appData/config.properties");
        if (Objects.equals(applicationProperties.getProperty("THEME"), "DARK")) {
            themeProperties = getProperties("/style/darkTheme.properties");
        } else {
            themeProperties = getProperties("/style/lightTheme.properties");
        }
        SqlCommandRunner.setUrl(applicationProperties.getProperty("DatabaseURL"));
        SqlCommandRunner.setUser(applicationProperties.getProperty("DatabaseUser"));
        SqlCommandRunner.setPassword(applicationProperties.getProperty("DatabasePassword"));
        try {
            SqlCommandRunner.openSqlConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
        logger.info("Connection to Database Successful");

        openApp();
    }

    /**
     * Open app.
     */
    public void openApp() {
        logger.info("Opening App Window");

        appWindow = new AppWindow(stage, width, height);

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
            System.exit(2);
        } catch (NullPointerException e) {
            logger.info("Database not yet initialised");
            System.exit(6);
        }
        System.exit(0);
    }


    private static Properties getProperties(String fileURL) {
        Properties properties = new Properties();
        InputStreamReader inputStream;
        try {
            inputStream = new InputStreamReader(Objects.requireNonNull(App.class.getResourceAsStream(fileURL)));
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(6);
        }
        return properties;
    }
    private static void setProperty(String fileUrl, String key, String value) {
        try {
            InputStream URL = App.class.getResourceAsStream(fileUrl);
            FileOutputStream outputStream = new FileOutputStream(Objects.requireNonNull(URL).toString());
            //TODO: Issue Here
            applicationProperties.setProperty(key, value);
            applicationProperties.store(outputStream, null);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
    }

    public static void toggleTheme(BaseScene scene) {
        if (Objects.equals(applicationProperties.getProperty("THEME"), "DARK")) {
            setProperty("/appData/config.properties", "THEME", "LIGHT");
            themeProperties = getProperties("/style/lightTheme.properties");
        } else {
            setProperty("/appData/config.properties", "THEME", "DARK");
            themeProperties = getProperties("/style/darkTheme.properties");
        }
        appWindow.loadScene(scene);
    }

    public static String getVersion() {
        return applicationProperties.getProperty("VERSION");
    }
}