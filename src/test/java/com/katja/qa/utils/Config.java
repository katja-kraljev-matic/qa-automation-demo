package com.katja.qa.utils;

/**
 * Central place for settings. Override on the command line, e.g.
 * mvn test -Dheadless=true -DbaseUrl=https://www.saucedemo.com
 */
public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://www.saucedemo.com");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
