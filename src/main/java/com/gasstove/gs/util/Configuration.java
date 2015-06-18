package com.gasstove.gs.util;

/**
 * Configuration parameters
 */
public class Configuration {

    public static String testDBBackup = "src/main/resources/gasstoveTest_backup.db";
    public static String testDB = "src/main/resources/gasstoveTest.db";
    public static String devDB = "src/main/resources/gasstoveDev.db";

    public static final String SERVER_BASE_URI = "http://localhost:50000";
    public static final String REST_API_PATH = "/gs-rest-api";
    public static final String dbConnect = "jdbc:sqlite:" + Configuration.devDB;

    //public static String prodDB = "jdbc:mysql://52.10.166.27:3306/gasstove";
    //public static final String dbConnect = Configuration.prodDB;
    //public static final String PROD_SERVER_BASE_URI = "http://52.10.166.27:8080";

}
