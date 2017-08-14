package com.example.ibokan.yazlab4;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibokan on 23.05.2017.
 */

public class Detay extends AppCompatActivity {
    TextView id,il,tip,alan,oda,yas,kat,fiyat,aciklama;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detay);
        id= (TextView) findViewById(R.id.textView2);
        il= (TextView) findViewById(R.id.textView3);
        tip= (TextView) findViewById(R.id.textView4);
        alan= (TextView) findViewById(R.id.textView5);
        oda= (TextView) findViewById(R.id.textView6);
        yas= (TextView) findViewById(R.id.textView7);
        kat= (TextView) findViewById(R.id.textView8);
        fiyat= (TextView) findViewById(R.id.textView9);
        aciklama= (TextView) findViewById(R.id.textView10);;

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String strid = b.getString("id");
        //StrictMode kullanarak,ağ erişiminin güvenli bir şekilde yapılmasını sağlıyoruz...
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String URL="http://192.168.2.6:8080/yazlab/detay?id="+strid;
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

        try
        {
            String result="";
            HttpClient httpclient=new DefaultHttpClient();
            HttpResponse response=httpclient.execute(new HttpGet(URL));
            BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            result=reader.readLine();
            JSONObject json=new JSONObject(result);
            String home_id=json.getString("evID");
            String home_il=json.getString("evIL");
            String home_tip=json.getString("evEmlakTip");
            String home_alan=json.getString("evAlan");
            String home_oda=json.getString("evOdaSayisi");
            String home_yas=json.getString("evBinaYasi");
            String home_kat=json.getString("evBulKat");
            String home_fiyat=json.getString("evFiyat");
            String home_aciklama=json.getString("evAciklama");
            id.setText(home_id);
            il.setText(home_il);
            tip.setText(home_tip);
            alan.setText(home_alan);
            oda.setText(home_oda);
            yas.setText(home_yas);
            kat.setText(home_kat);
            fiyat.setText(home_fiyat);
            aciklama.setText(home_aciklama);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

