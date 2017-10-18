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

    public FetchCoins(MainActivity mainActivity){
        activity = mainActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            BufferedReader coinReader = internet.setUpWebComponents(new URL("https://shapeshift.io/getcoins"));

            //Sets the String coins to every coin from /getcoins on shapeshift
            String line;
            for (line = coinReader.readLine(); line != null; line = coinReader.readLine()) {
                if(coins != null) coins = coins + line;
                else coins = line;
                System.out.println("COINS: " + coins);
            }

            coins = "[" + coins + "]"; //Encloses the String coins with square brackets to be added to JSONArray

            JSONArray coinArray = new JSONArray(coins); //Creates a JSONArray from the String coins

            //Adding each coin (symbol) key from coinArray to coinsList
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //If the list is populated, remove the button populateButton
        if(coinsList != null) {
            ((RelativeLayout) activity.populateButton.getParent()).removeView(activity.populateButton);
        }

        //Setup an adapter for the spinners using coinsList and notify the data set has changed
        activity.setupAdapter(coinsList);
        activity.dataAdapter.notifyDataSetChanged();
    }
}