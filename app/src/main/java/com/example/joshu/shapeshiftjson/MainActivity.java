package com.example.joshu.shapeshiftjson;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button showJSONData;
    public static TextView jsonData;
    static ArrayAdapter<String> dataAdapter;
    Spinner currencyIn, currencyOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyIn = (Spinner) findViewById(R.id.currencyIn);
        currencyOut = (Spinner) findViewById(R.id.currencyOut);

        currencyIn.setOnItemSelectedListener(this);
        currencyOut.setOnItemSelectedListener(this);

        showJSONData = (Button) findViewById(R.id.button);
        jsonData = (TextView) findViewById(R.id.fetcheddata);

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, fetchData.coinsList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        currencyIn.setAdapter(dataAdapter);
        currencyOut.setAdapter(dataAdapter);

        showJSONData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + view.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




