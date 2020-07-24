package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    Button priceBtn;
    Button idBtn;
    Button skuBtn;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MainActivity() throws IOException {


    }

    public static StringBuilder data = null;


    public static void getId() throws JSONException {
        JSONObject js = new JSONObject(String.valueOf(data));
        // convert to data in the variable 'data' to JSON Object.
        JSONArray arr = js.getJSONArray("items");
        for (int i = 0; i < arr.length(); i++)
        {
            String id = arr.getJSONObject(i).getString("id");
        }
    }

    public static void getSku() throws JSONException {
        JSONObject js = new JSONObject(String.valueOf(data));
        JSONArray arr = js.getJSONArray("items");
        for (int i = 0; i < arr.length(); i++)
        {
            String sku = arr.getJSONObject(i).getString("sku");
        }
    }

    public static void getPrice() throws JSONException {
        JSONObject js = new JSONObject(String.valueOf(data));
        JSONArray arr = js.getJSONArray("items");
        for (int i = 0; i < arr.length(); i++)
        {
            String price = arr.getJSONObject(i).getString("price");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpsTrustManager ht = new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        // the SSL of the API Source is not secure, so we have to disable SSL verification

        URL url = null;
        try {
            url = new URL("https://magento-11086-0.cloudclusters.net/rest/V1/products?searchCriteria");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)  url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Authorization", "Bearer paq8t1a4wub9pn1atyxuv5zndrqmxtov");
        try {
            try {
                InputStream is = urlConnection.getInputStream();
                Reader reader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                data = builder;
                // we got all data as string and stored in the builder.
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            urlConnection.disconnect();
            try {
                getId();
                getSku();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        priceBtn = findViewById(R.id.price);
        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getPrice();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        idBtn = findViewById(R.id.productId);
        idBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getId();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        skuBtn = findViewById(R.id.sku);
        skuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getSku();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}