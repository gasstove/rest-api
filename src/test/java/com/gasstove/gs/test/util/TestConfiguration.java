package com.gasstove.gs.test.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestConfiguration {

  public static final String SERVER_BASE_URI = "http://localhost:50000";
  public static final String REST_API_PATH = "/gs-rest-api";

  public static HttpURLConnection sendRequest(String uri, String method, String data) {

    try {
      String url = SERVER_BASE_URI + REST_API_PATH + uri;

      // Construct Http Connection object
      URL requestUrl = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
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
      exp.printStackTrace();
      return null;
    }
  }
}