package com.example.joshu.shapeshiftjson;

import android.view.View;
import android.widget.AdapterView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

class CurrencySpinnerListener implements AdapterView.OnItemSelectedListener{
    private String curIn, curOut;
    static String pair;
    private MainActivity activity;

    CurrencySpinnerListener(MainActivity a){
        activity = a;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Map<String, ?> allEntries = MainActivity.sharedPref.getAll();
        // On selecting a spinner item
        activity.favorite.setChecked(false);
        // Sets the selected value
        switch (parent.getId()) {
            case com.example.joshu.shapeshiftjson.R.id.currencyIn:
                curIn = parent.getItemAtPosition(position).toString();
                System.out.println(curIn);
                break;
            case com.example.joshu.shapeshiftjson.R.id.currencyOut:
                curOut = parent.getItemAtPosition(position).toString();
                System.out.println(curOut);
                break;
        }
        try {
            pair = curIn + "_" + curOut;
            // Sets the URL in Internet to the URL to receive a rate between two currencies
            Internet.url = new URL("https://shapeshift.io/rate/" + curIn + "_" + curOut);
            System.out.println(Internet.url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if(pair != null) {
                //If any of the StoredPreferences are the current pair, check the favorites box and return
                if ((entry.getValue()).equals(pair)) {
                    activity.favorite.setChecked(true);
                    return;
                } else {
                    activity.favorite.setChecked(false);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
