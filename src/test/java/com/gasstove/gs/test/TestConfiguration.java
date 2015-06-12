package com.gasstove.gs.test;

import com.gasstove.gs.util.Configuration;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestConfiguration {

    public static final String db = "jdbc:sqlite:" + Configuration.testDB;

    public static Integer user_id = 36;
    public static Integer event_id = 1;
    public static Integer media_id = 15;
    public static Integer mediaevent_id = 1;
    public static Integer userevent_id = 1;


    // standard response values per test
//    public static String expectedResponseStatus = "OK";
//    public static int expectedResponseCode = 200;

    public static HttpURLConnection sendRequest(String uri, String method, int number) {
        return sendRequest(uri,method,Integer.toString(number));
    }

    public static HttpURLConnection sendRequest(String uri, String method, String data) {

        HttpURLConnection conn = null;

        try {
            String url = Configuration.SERVER_BASE_URI + Configuration.REST_API_PATH + uri;

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