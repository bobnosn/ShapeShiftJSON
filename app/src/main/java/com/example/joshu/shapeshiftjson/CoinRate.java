package com.example.joshu.shapeshiftjson;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by joshu on 10/16/2017.
 */
class CoinRate extends AsyncTask<Void, Void, Void> {
    Internet internet = new Internet();
    private MainActivity activity;
    private Context context;

    public CoinRate(MainActivity mainActivity) {
        activity = mainActivity;
    }

    protected Void doInBackground(Void... voids) {
        internet.parseData();
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.jsonData.setText(Internet.dataParsed);
            }
        });


    }
}