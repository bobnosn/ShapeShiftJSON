package com.example.joshu.shapeshiftjson;

import android.widget.Toast;

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
    private MainActivity mainActivityContext;

    Internet(MainActivity mainActivity){ mainActivityContext = mainActivity; }

    void parseData(int type) {

        Map<String, ?> keys = MainActivity.sharedPref.getAll();
        //If the rates to be gotten are for the favorite rates, iterate through all the shared prefs and make a new URL for each saved pref
        int FAVORITE = 0, RATE = 1;
        float i = 1;
        if (type == FAVORITE) {
            try {
                for (Map.Entry<String, ?> entry : keys.entrySet()) {
                    mainActivityContext.favoriteProgressBar.setProgress((int)((i/keys.size())*100));
                    url = new URL("https://shapeshift.io/rate/" + entry.getKey());
                    setSpecificRate();
                    i++;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (type == RATE) {
            setSpecificRate();
        }
    }

    private void setSpecificRate() {
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
        data = "[" + data + "]";

        try {
            JSONArray JA = new JSONArray(data);
            JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

            String singleParsed = "pair: " + JO.get("pair") + "\n" + "rate: " + JO.get("rate") + "\n";
            dataParsed = dataParsed != null ? dataParsed + singleParsed + "\n" : singleParsed + "\n";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return data;
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
