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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class fetchData extends AsyncTask<Void, Void, Void> {
    private String data = "";
    private String coins = "";
    private String dataParsed = "";


    static List<String> coinsList = new ArrayList<>();
    //URL url = new URL("https://shapeshift.io/rate/eth_xmr");
    private URL url; //url = new URL("https://shapeshift.io/getcoins");
    //URL url = new URL("https://shapeshift.io/recenttx/10");

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            BufferedReader coinReader = setUpWebComponents(new URL("https://shapeshift.io/getcoins"));
            BufferedReader urlReader = setUpWebComponents(new URL("https://shapeshift.io/recenttx/10"));
            url = new URL("https://shapeshift.io/recenttx/10");

            String line;

            for (line = urlReader.readLine(); line != null; line = urlReader.readLine()) {
                data = data+line;
                System.out.println("Data: "+data);
            }

            for (line = coinReader.readLine(); line != null; line = coinReader.readLine()) {
                coins = coins + line;
                System.out.println("COINS: " + coins);
            }
            coins = "[" + coins + "]";

            JSONArray coinArray = new JSONArray(coins);

            //Adding each coin key (symbol) from coinArray to coinsList
            for (int i = 0; i < coinArray.length(); i++) {
                JSONObject coins = coinArray.getJSONObject(i);
                Iterator key = coins.keys();
                while (key.hasNext()) {
                    String k = key.next().toString();
                    System.out.println("Key.next(): " + k);
                    coinsList.add(i, k);
                    //System.out.println("Key : " + k + ", value : " + objects.getString(k));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //br.close();

    private BufferedReader setUpWebComponents(URL urll) {
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

    private void getData() {
        String singleParsed = null;
        try {
            switch (url.toString()) {
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
                    data = "[" + data + "]";
                    JA = new JSONArray(data); //Set the JA JSONArray to the contents of the data variable
                    JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

                    singleParsed = "pair: " + JO.get("pair") + "\n" +
                            "rate: " + JO.get("rate") + "\n"; //Parse the data of the object
                    dataParsed = dataParsed + singleParsed + "\n";
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.jsonData.setText(this.dataParsed);
        //return dataParsed;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        getData();
        //MainActivity.jsonData.setText(this.dataParsed);

    }
}