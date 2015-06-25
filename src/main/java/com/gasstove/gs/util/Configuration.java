package com.gasstove.gs.util;

/**
 * Configuration parameters
 */
public class Configuration {

//    public static final String PROD_SERVER_BASE_URI = "http://52.10.166.27:8080";

    ////////////////////////////////////////////////////////
    // PROFILES
    ////////////////////////////////////////////////////////

    public enum PROFILE { test , dev , prod }

    public static PROFILE profile  = PROFILE.dev;

    public static String getDB(){
        return getDBPrefix() + getDBLink();
    }

    public static String getDBLink(){

        switch( Configuration.profile ) {
            case test:
                return "src/main/resources/gasstoveTest.db";
            case dev:
                return "src/main/resources/gasstoveDev.db";
            case prod:
                return "//localhost:3306/gasstove?user=root&password=";
            default:
                return "";
        }
    }

    public static String getDBPrefix(){
        switch( Configuration.profile ) {
            case test:
            case dev:
                return "jdbc:sqlite:";
            case prod:
                return "jdbc:mysql:";
            default:
                return "";
        }
    }

    public static String getDriverClassPath(){
        switch( Configuration.profile ) {
            case test:
            case dev:
                return "org.sqlite.JDBC";
            case prod:
                return "com.mysql.jdbc.Driver";
            default:
                return "";
        }
    }

}
