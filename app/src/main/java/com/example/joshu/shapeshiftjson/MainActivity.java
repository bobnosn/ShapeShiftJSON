package com.example.joshu.shapeshiftjson;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner currencyIn = (Spinner) findViewById(R.id.currencyIn);
        Spinner currencyOut = (Spinner) findViewById(R.id.currencyOut);

        currencyIn.setOnItemSelectedListener(this);
        currencyOut.setOnItemSelectedListener(this);

        showJSONData = (Button) findViewById(R.id.button);
        jsonData = (TextView) findViewById(R.id.fetcheddata);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fetchData.coinsList);

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
        String item = fetchData.coinsList.get(position);

        System.out.println(fetchData.coinsList.get(position));
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




