package com.example.joshu.shapeshiftjson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.joshu.reworkedshapeshiftjson.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button showJSONData, populateButton;
    public TextView jsonData;
    ArrayAdapter<String> dataAdapter;
    Spinner currencyIn, currencyOut;
    Context uiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiContext = this;

        currencyIn = (Spinner) findViewById(R.id.currencyIn);
        currencyOut = (Spinner) findViewById(R.id.currencyOut);

        CurrencySpinnerListener csl = new CurrencySpinnerListener();
        currencyIn.setOnItemSelectedListener(csl);
        currencyOut.setOnItemSelectedListener(csl);

        populateButton = (Button) findViewById(R.id.populateButton);
        showJSONData = (Button) findViewById(R.id.button);
        jsonData = (TextView) findViewById(R.id.fetcheddata);

        populateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchCoins process = new FetchCoins(MainActivity.this);
                process.execute();
            }
        });
        showJSONData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoinRate coinRate = new CoinRate(MainActivity.this);
                coinRate.execute();
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