package com.example.joshu.shapeshiftjson;

import android.view.View;
import android.widget.AdapterView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joshu on 10/16/2017.
 */

public class CurrencySpinnerListener implements AdapterView.OnItemSelectedListener{
    String curIn, curOut;
    Internet internet = new Internet();

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        // Sets the selected value
        switch (parent.getId()) {
            case com.example.joshu.reworkedshapeshiftjson.R.id.currencyIn:
                curIn = parent.getItemAtPosition(position).toString();
                System.out.println(curIn);
                break;
            case com.example.joshu.reworkedshapeshiftjson.R.id.currencyOut:
                curOut = parent.getItemAtPosition(position).toString();
                System.out.println(curOut);
                break;
        }
        try {
            // Sets the URL in Internet to the URL to receive a rate between two currencies
            Internet.url = new URL("https://shapeshift.io/rate/" + curIn + "_" + curOut);
            System.out.println(internet.url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
