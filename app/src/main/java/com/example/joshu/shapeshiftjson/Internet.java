package com.example.joshu.shapeshiftjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joshu on 10/16/2017.
 */

public class Internet {
    static URL url;
    String data;
    static String dataParsed;

    public void parseData() {
        String singleParsed;
        JSONArray JA;
        try {
            data = getSpecificRate(); //Sets String data to the rate between two currencies returned by getSpecificRate

            data = "[" + data + "]";

            JA = new JSONArray(data); //Set the JA JSONArray to the contents of the data variable
            JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

            singleParsed = "pair: " + JO.get("pair") + "\n" + "rate: " + JO.get("rate") + "\n"; //Parse the data of the object

            if (dataParsed != null) {
                dataParsed = dataParsed + singleParsed + "\n";
            } else {
                dataParsed = singleParsed;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSpecificRate() {
        String line;
        if(data!=null) data = null;
        BufferedReader urlReader = setUpWebComponents(url);
        try {
            //Gets a rate between two selected currencies from the Spinners currencyIn and currencyOut
            for (line = urlReader.readLine(); line != null; line = urlReader.readLine()) {
                if (data!=null) data = data + line;
                else data = line;
                System.out.println("Data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlReader != null) {
                //Closes the urlReader after it has been used
                try {
                    urlReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public BufferedReader setUpWebComponents(URL urll) {
        //Sets up and returns BufferedReader bufferedReader for a specific URL
        BufferedReader bufferedReader = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urll.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }
}
