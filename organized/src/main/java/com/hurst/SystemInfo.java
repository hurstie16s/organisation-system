package com.hurst;

/**
 * The type System info.
 */
public class SystemInfo {

    /**
     * Java version string.
     *
     * @return the string
     */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Javafx version string.
     *
     * @return the string
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}