package com.londonappbrewery.bitcointicker;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinDataModel  {
    //  TODO: Declare the member variables here

    private String qTextValue;

    public static BitcoinDataModel fromJSON(JSONObject jsonObject, String nameBitcoin){
        try {
            BitcoinDataModel bitcoinDataModel = new BitcoinDataModel();
            Log.d("mTest","Running on BitcoinDataModel");

            double numberOfValue =(double) jsonObject.getJSONObject("BTC"+nameBitcoin).getJSONObject("averages").getInt("day");
            Log.d("mTest","Got numberOfValue");
            bitcoinDataModel.qTextValue = Integer.toString((int)Math.rint((int)numberOfValue));
            Log.d("mTest","get qTextValue : "+bitcoinDataModel.qTextValue);
            return bitcoinDataModel;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }

    }

    public String getqTextValue() {
        return qTextValue;
    }
}
