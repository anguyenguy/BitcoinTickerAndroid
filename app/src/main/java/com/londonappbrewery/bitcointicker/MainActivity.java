package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL =
            "https://apiv2.bitcoinaverage.com/indices/global/ticker/short?crypto=BTC&fiat=USD,AUD,BRL,CAD,CNY," +
                    "EUR,GBP,HKD,JPY,PLN,RUB,SEK,ZAR";

    // Member Variables:
    TextView mPriceTextView;
    String mNameCurrentcy="AUD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {mNameCurrentcy="AUD"; break;}
                    case 1: {mNameCurrentcy="BRL"; break;}
                    case 2: {mNameCurrentcy="CAD"; break;}
                    case 3: {mNameCurrentcy="CNY"; break;}
                    case 4: {mNameCurrentcy="EUR"; break;}
                    case 5: {mNameCurrentcy="GBP"; break;}
                    case 6: {mNameCurrentcy="HKD"; break;}
                    case 7: {mNameCurrentcy="JPY"; break;}
                    case 8: {mNameCurrentcy="PLN"; break;}
                    case 9: {mNameCurrentcy="RUB"; break;}
                    case 10:{mNameCurrentcy="SEK"; break;}
                    case 11:{mNameCurrentcy="USD"; break;}
                    case 12:{mNameCurrentcy="ZAR"; break;}
                }
                Log.d("mTest","User selected: " + mNameCurrentcy);
                RequestParams params = new RequestParams();
                letsDoSomethingNetworking(params);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomethingNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler(){

          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("mTest","JSON successed! :"+response);
                BitcoinDataModel bitcoinDataModel = BitcoinDataModel.fromJSON(response, mNameCurrentcy);

                mPriceTextView.setText(bitcoinDataModel.getqTextValue());


          }

          @Override
          public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("mTest", "Request fail! Status code: " + statusCode);
                Log.d("mTest", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
