package com.gulam.utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProcessJsonResponse {
    /********************************************/
    /* Global function for reading Json Data    */
    /* @input: apiURL                           */
    /* @return: JsonObject                      */
    /********************************************/

    public static JSONObject getJsonResponse(String apiURL){

        URL url = null;

        JSONObject responseObjFinal = null;

        try {
            url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject responseObj = new JSONObject(response.toString());

            responseObjFinal = responseObj;

        } catch (IOException e){
            e.printStackTrace();
        }
        return responseObjFinal;
    }
}
