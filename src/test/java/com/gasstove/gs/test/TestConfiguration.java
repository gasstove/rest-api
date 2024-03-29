package com.gasstove.gs.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestConfiguration {

    private static final String SERVER_BASE_URI = "http://localhost:50000";
    private static final String REST_API_PATH = "/gs-rest-api";

//    public static final String db = "jdbc:sqlite:" + Configuration.testDB;

    public static Integer user_id = 36;
    public static Integer event_id = 1;
    public static Integer media_id = 15;
    public static Integer mediaevent_id = 1;
    public static Integer userevent_id = 1;

    public static boolean doprint = false;

    public static void printout(String response,String expected,String methodname){
        if(TestConfiguration.doprint) {
            System.out.println(methodname);
            System.out.println(response);
            System.out.println(expected);
        }
    }

    // standard response values per test
//    public static String expectedResponseStatus = "OK";
//    public static int expectedResponseCode = 200;

    public static HttpURLConnection sendRequest(String uri, String method, int number) {
        return sendRequest(uri,method,Integer.toString(number));
    }

    public static HttpURLConnection sendRequest(String uri, String method, String data) {

        HttpURLConnection conn = null;

        try {
            String url = TestConfiguration.SERVER_BASE_URI + TestConfiguration.REST_API_PATH + uri;

            // Construct Http Connection object
            URL requestUrl = new URL(url);
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestMethod(method);

            if(method.equals("POST") || method.equals("PUT")){
                byte[] outputBytes = data.getBytes();
                conn.setFixedLengthStreamingMode(outputBytes.length);
                conn.connect();
                OutputStream os = conn.getOutputStream();
                os.write(outputBytes);
                os.flush();
                os.close();
            }
            else{
                conn.connect();
            }

            // return response
            return conn;

        }
        catch (Exception exp) {
            if(conn!=null)
                conn.disconnect();
            exp.printStackTrace();
            return null;
        }
    }

}