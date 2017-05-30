package com.gkortsaridis.androidthingsmagicmirror;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DigitalClock;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {


    TextView mClockView,mWeather,mDate;
    Handler h = new Handler();
    Handler h1 = new Handler();

    int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeather = (TextView) findViewById(R.id.weatherTV);
        mClockView = (TextView) findViewById(R.id.timeTV);
        mDate = (TextView) findViewById(R.id.dateTV);


        h1.postDelayed(new Runnable(){
            public void run(){
                getData();
                //do something
                h1.postDelayed(this, delay*60);
            }
        }, delay);

        h.postDelayed(new Runnable(){
            public void run(){
                ArrayList<String> days = new ArrayList<String>();
                days.add("Sunday");
                days.add("Monday");
                days.add("Tuesday");
                days.add("Wednesday");
                days.add("Thursday");
                days.add("Friday");
                days.add("Saturday");


                TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
                Calendar c = Calendar.getInstance(tz);

                String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+ String.format("%02d" , c.get(Calendar.MINUTE));
                String date = days.get((c.get(Calendar.DAY_OF_WEEK)-1))+" "+c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);

                mDate.setText(date);
                mClockView.setText(time);
                //do something
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    public void getData(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Mashape-Key", "dIC1resTTMmshi7pr5jLIGZYGcO3p1oLBs0jsnGOQJ7Q6b0lXz");
        client.addHeader("Accept", "text/plain");
        setRequestedOrientation(getRequestedOrientation());


        client.get(getBaseContext(), "https://simple-weather.p.mashape.com/weather?lat=40.5785522&lng=22.937711",  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final byte[] response) {
                String resp = new String(response);
                mWeather.setText(resp);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                mWeather.setText("FAIL "+statusCode);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        });


    }






}
