package com.example.joshu.shapeshiftjson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button rateButton, populateButton; //Buttons to 1. get and show a rate and 2. populate spinners with coins
    public TextView jsonData; //Holds the text of the received rate(s)
    ArrayAdapter<String> dataAdapter; //Adapter for both currencyIn and currencyOut spinners
    Spinner currencyIn, currencyOut; //Holds all possible coins after being populated by button populateButton
    Context uiContext; //Context of this (User Interface)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiContext = this; //Sets uiContext to the context for the User Interface

        currencyIn = (Spinner) findViewById(R.id.currencyIn);
        currencyOut = (Spinner) findViewById(R.id.currencyOut);

        //Creates CurrencySpinnerListener object (the custom onItemSelectedListener for both spinners) and sets it
        CurrencySpinnerListener csl = new CurrencySpinnerListener();
        currencyIn.setOnItemSelectedListener(csl);
        currencyOut.setOnItemSelectedListener(csl);

        populateButton = (Button) findViewById(R.id.populateButton);
        rateButton = (Button) findViewById(R.id.button);
        jsonData = (TextView) findViewById(R.id.fetcheddata);

        populateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchCoins process = new FetchCoins(MainActivity.this);
                process.execute(); //Runs doInBackground in FetchCoins to populate the spinners
            }
        });
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoinRate coinRate = new CoinRate(MainActivity.this);
                coinRate.execute(); //Runs doInBackground in CoinRate to get a rate between two selected currencies
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


}