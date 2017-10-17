package com.example.joshu.shapeshiftjson;

import android.os.AsyncTask;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joshu on 10/16/2017.
 */

class FetchCoins extends AsyncTask<Void, Void, Void> {
    private String coins = "";
    Internet internet = new Internet();
    List<String> coinsList = new ArrayList<>();
    MainActivity activity;
    //URL url = new URL("https://shapeshift.io/recenttx/10");

    public FetchCoins(MainActivity mainActivty){
        activity = mainActivty;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            BufferedReader coinReader = internet.setUpWebComponents(new URL("https://shapeshift.io/getcoins"));

            String line;

            for (line = coinReader.readLine(); line != null; line = coinReader.readLine()) {
                coins = coins + line;
                System.out.println("COINS: " + coins);
            }
            coins = "[" + coins + "]";

            JSONArray coinArray = new JSONArray(coins);

            //Adding each coin key (symbol) from coinArray to coinsList

            JSONObject coins = coinArray.getJSONObject(0);
            Iterator key = coins.keys();
            while (key.hasNext()) {
                String k = key.next().toString();
                System.out.println("Key.next(): " + k);
                coinsList.add(0, k);
                //System.out.println("Key : " + k + ", value : " + objects.getString(k));
            }
            if(coinsList != null) {
                //coinReader.close();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //br.close();

/*
            private void parseData() {
                String singleParsed = null;

                    case "https://shapeshift.io/recenttx/10":
                        JSONArray JA = new JSONArray(data);
                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject JO = (JSONObject) JA.get(i);
                            singleParsed = "curIn: " + JO.get("curIn") + "\n" +
                                    "curOut: " + JO.get("curOut") + "\n" +
                                    "timestamp: " + JO.get("timestamp") + "\n" +
                                    "amount: " + JO.get("amount") + "\n";

                            dataParsed = dataParsed + singleParsed + "\n";
                        }*/


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //parseData();
        //MainActivity.jsonData.setText(this.dataParsed);
        if(coinsList != null) {
            ((RelativeLayout) activity.populateButton.getParent()).removeView(activity.populateButton);
        }
        activity.setupAdapter(coinsList);
        activity.dataAdapter.notifyDataSetChanged();
    }
}