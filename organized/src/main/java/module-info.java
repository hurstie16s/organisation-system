module com.hurst {
    requires javafx.controls;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires mysql.connector.java;

    opens com.hurst to javafx.base;

    exports com.hurst;
    exports com.hurst.scene;
    exports com.hurst.ui;
}