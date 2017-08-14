package com.example.ibokan.yazlab4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title= (TextView) findViewById(R.id.textTitle);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/com.ttf");
        title.setTypeface(face);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String URL="http://192.168.2.6:8080/yazlab/list";
                                try
                                {
                                    String result="";
                                    HttpClient httpclient=new DefaultHttpClient();
                                    HttpResponse response=httpclient.execute(new HttpGet(URL));
                                    BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
                                    result=reader.readLine();
                                    JSONArray proveedors=new JSONArray(result);
                                    List<Map<String,String>> data=new ArrayList<>();
                                    for (int i=0;i<proveedors.length();i++) {
                                        JSONObject json=proveedors.getJSONObject(i);
                                        Map<String , String> map=new HashMap<>(2);
                                        map.put("evID",json.getString("evID"));
                                        map.put("evOdaSayisi",json.getString("evOdaSayisi"));
                                        map.put("evEmlakTip",json.getString("evEmlakTip"));
                                        map.put("evFiyat",json.getString("evFiyat"));
                                        data.add(map);
                                    }
                                    SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,data,R.layout.custom_list_layout,
                                            new String[]{"evID","evOdaSayisi","evEmlakTip","evFiyat"},new int[]{R.id.txtId,R.id.txtOda,R.id.txtTip,R.id.txtPrice });
                                    final ListView listView= (ListView) findViewById(R.id.txtlist);
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                                            TextView et = (TextView) view.findViewById(R.id.txtId);
                                            String value=et.getText().toString();
                                            //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(MainActivity.this,Detay.class);
                                            intent.putExtra("id", value);
                                            startActivity(intent);

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ClientProtocolException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };t.start();



    }
}


