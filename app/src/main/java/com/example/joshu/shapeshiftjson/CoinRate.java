package com.example.joshu.shapeshiftjson;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

// This is really only here because I can't call network operations from MainActivity
class CoinRate extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;
    private Internet internet;

    CoinRate(MainActivity mainActivity) {
        activity = mainActivity;
        internet = new Internet(activity);
    }

    protected Void doInBackground(Void... voids) {
        internet.parseData(1); //Sets the data for dataParsed in Internet to the parsed data of a rate between two currencies
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Sets the text of jsonData in MainActivity to the data of dataParsed in Internet
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.jsonData.setText(Internet.dataParsed);
            }
        });


    }
}