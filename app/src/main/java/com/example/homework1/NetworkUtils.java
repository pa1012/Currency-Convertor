package com.example.homework1;

import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

  public static final String CURRENCY_CONVERTER_URL= "https://free.currconv.com/api/v7/convert";
  public static final String QUERY_PARAM = "q";
  public static final String FORMAT_PARAM = "compact";
  public static final String API_KEY="apiKey";

  static Bundle getInfo(String queryString, int ID) {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String results = null;

    //build the URI
    try {
      Uri builtURI = Uri.parse(CURRENCY_CONVERTER_URL).buildUpon()
              .appendQueryParameter(QUERY_PARAM, queryString)
              .appendQueryParameter(FORMAT_PARAM, "ultra")
              .appendQueryParameter(API_KEY, "f60a5f63275420f7c024")
              .build();
      URL requestURL = new URL(builtURI.toString());

      urlConnection = (HttpURLConnection) requestURL.openConnection();
      urlConnection.connect();

      // Get the InputStream.
      InputStream inputStream = urlConnection.getInputStream();

      // Create a buffered reader from that input stream.
      reader = new BufferedReader(new InputStreamReader(inputStream));

      // Use a StringBuilder to hold the incoming response.
      StringBuilder builder = new StringBuilder();

      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
        // Since it's JSON, adding a newline isn't necessary (it won't
        // affect parsing) but it does make debugging a *lot* easier
        // if you print out the completed buffer for debugging.
        builder.append("\n");
        if (builder.length() == 0) {
          // Stream was empty. No point in parsing.
          return null;
        }
        results = builder.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    Bundle bundle = new Bundle();
    bundle.putString("val", results);
    bundle.putInt("pos", ID);
    return bundle;
  }

}
