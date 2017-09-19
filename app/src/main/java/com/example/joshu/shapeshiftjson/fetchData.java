package com.example.joshu.shapeshiftjson;

import android.os.AsyncTask;

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

/**
 * Created by Josh Gerber on 9/15/2017.
 */

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://shapeshift.io/rate/eth_xmr");
            //URL url = new URL("https://shapeshift.io/recenttx/10");

            //Setup to read from URL
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            /*while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;

                System.out.println(data);
            }*/
            for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                data = data+line;
                System.out.println("Data: "+data);
            }

            //br.close();

            //JSONArray JA = new JSONArray(data);
            //System.out.println(url);
            //System.out.println("JSONArray: "+JA);
            //System.out.println("JSONArray.length: "+JA.length());

            switch(url.toString()){
                case "https://shapeshift.io/recenttx/10":
                    JSONArray JA = new JSONArray(data);
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject JO = (JSONObject) JA.get(i);
                        singleParsed = "curIn: " + JO.get("curIn") + "\n" +
                                "curOut: " + JO.get("curOut") + "\n" +
                                "timestamp: " + JO.get("timestamp") + "\n" +
                                "amount: " + JO.get("amount") + "\n";

                        dataParsed = dataParsed + singleParsed + "\n";
                    }
                    break;
                case "https://shapeshift.io/rate/eth_xmr":
                    //For some reason, the JSON contents aren't surrounded by square brackets when the data doesn't contain a list
                    data = "["+data+"]";
                    JA = new JSONArray(data); //Set the JA JSONArray to the contents of the data variable
                    JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

                    singleParsed = "pair: " + JO.get("pair") + "\n" +
                            "rate: " + JO.get("rate") + "\n"; //Parse the data of the object
                    dataParsed = dataParsed + singleParsed + "\n";

                    break;
                default:
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.jsonData.setText(this.dataParsed);

    }
}