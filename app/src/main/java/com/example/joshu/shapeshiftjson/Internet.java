package com.example.joshu.shapeshiftjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


class Internet {
    static URL url;
    private String data;
    static String dataParsed;
    private final int FAVORITE = 0, RATE = 1;

    void parseData(int type) {
        String singleParsed;
        JSONArray JA;
        Map<String, ?> keys = MainActivity.sharedPref.getAll();
        if (type == FAVORITE) {
            try {
                for (Map.Entry<String, ?> entry : keys.entrySet()) {
                    url = new URL("https://shapeshift.io/rate/" + entry.getKey());
                    data = getSpecificRate();

                    data = "[" + data + "]";

                    JA = new JSONArray(data);
                    JSONObject JO = (JSONObject) JA.get(0);

                    singleParsed = "pair: " + JO.get("pair") + "\n" + "rate: " + JO.get("rate") + "\n";

                    dataParsed = dataParsed != null ? dataParsed + singleParsed + "\n" : singleParsed + "\n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (type == RATE) {
            try {
                data = getSpecificRate(); //Sets String data to the rate between two currencies returned by getSpecificRate

                data = "[" + data + "]";

                JA = new JSONArray(data); //Set the JA JSONArray to the contents of the data variable
                JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

                singleParsed = "pair: " + JO.get("pair") + "\n" + "rate: " + JO.get("rate") + "\n"; //Parse the data of the object

                dataParsed = dataParsed != null ? dataParsed + singleParsed + "\n" : singleParsed + "\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSpecificRate() {
        String line;

        if (data != null) data = null;

        BufferedReader urlReader = setUpWebComponents(url);
        try {
            //Gets a rate between two selected currencies from the Spinners currencyIn and currencyOut
            for (line = urlReader.readLine(); line != null; line = urlReader.readLine()) {
                data = line;
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

    BufferedReader setUpWebComponents(URL url) {
        //Sets up and returns BufferedReader bufferedReader for a specific URL
        BufferedReader bufferedReader = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }
}
