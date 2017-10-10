package com.example.joshu.shapeshiftjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button showJSONData, populateButton;
    public TextView jsonData;
    ArrayAdapter<String> dataAdapter;
    Spinner currencyIn, currencyOut;
    String curIn, curOut;
    private URL url;
    private String data, dataParsed = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyIn = (Spinner) findViewById(R.id.currencyIn);
        currencyOut = (Spinner) findViewById(R.id.currencyOut);

        currencyIn.setOnItemSelectedListener(this);
        currencyOut.setOnItemSelectedListener(this);

        populateButton = (Button) findViewById(R.id.populateButton);
        showJSONData = (Button) findViewById(R.id.button);
        jsonData = (TextView) findViewById(R.id.fetcheddata);

        populateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });
        showJSONData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCoinRate getCoinRate = new getCoinRate();
                getCoinRate.execute();
            }
        });

    }

    public void setupAdapter(List<String> receivedArray) {

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, receivedArray);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        currencyIn.setAdapter(dataAdapter);
        currencyOut.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (parent.getId()) {
            case R.id.currencyIn:
                curIn = parent.getItemAtPosition(position).toString();
                System.out.println(curIn);
                break;
            case R.id.currencyOut:
                curOut = parent.getItemAtPosition(position).toString();
                System.out.println(curOut);
                break;
        }
        try {
            url = new URL("https://shapeshift.io/rate/" + curIn + "_" + curOut);
            System.out.println(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void parseData() {
        //data = getSpecificRate();
        String singleParsed;
        JSONArray JA;
        try {
            data = getSpecificRate();

            data = "[" + data + "]";
            JA = new JSONArray(data);

            JA = new JSONArray(data); //Set the JA JSONArray to the contents of the data variable
            JSONObject JO = (JSONObject) JA.get(0); //Set the JO JSONObject equal to the contents of the first index of JA

            singleParsed = "pair: " + JO.get("pair") + "\n" +
                    "rate: " + JO.get("rate") + "\n"; //Parse the data of the object
            dataParsed = dataParsed + singleParsed + "\n";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSpecificRate() {
        String line;
        if(data!=null) data = null;
        BufferedReader urlReader = setUpWebComponents(url);
        try {

            for (line = urlReader.readLine(); line != null; line = urlReader.readLine()) {
                if (data!=null) data = data + line;
                else data = line;
                System.out.println("Data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

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

    private class fetchData extends AsyncTask<Void, Void, Void> {
        //private String data = "";
        private String coins = "";
        //private String dataParsed = "";


        List<String> coinsList = new ArrayList<>();
        //URL url = new URL("https://shapeshift.io/rate/eth_xmr");
        // private URL url; //url = new URL("https://shapeshift.io/getcoins");
        //URL url = new URL("https://shapeshift.io/recenttx/10");

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                BufferedReader coinReader = setUpWebComponents(new URL("https://shapeshift.io/getcoins"));
                //BufferedReader urlReader = setUpWebComponents(new URL("https://shapeshift.io/rate/eth_xmr"));
                //url = new URL("https://shapeshift.io/recenttx/10");

                String line;

                    /*for (line = urlReader.readLine(); line != null; line = urlReader.readLine()) {
                        data = data + line;
                        System.out.println("Data: " + data);
                    }*/

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

            setupAdapter(coinsList);
            dataAdapter.notifyDataSetChanged();
        }
    }

    private class getCoinRate extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... voids) {
            parseData();
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            jsonData.setText(dataParsed);

        }
    }
}