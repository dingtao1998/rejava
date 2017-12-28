package com.dt.rejava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.dt.rejava.R.id.iv;


public class MainActivity extends AppCompatActivity {
    private ImageView im;
    private String url = "http://a3.att.hudong.com/25/88/01300543064509142398886939232_s.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = (ImageView) findViewById(iv);

        Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                Bitmap b  = getBitMap(s);
                return b;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap b) {
               im.setImageBitmap(b);
            }
        });

    }

    public Bitmap getBitMap(String s)  {

        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            Log.i("456","11111111111");
            e.printStackTrace();
        }
        HttpURLConnection huc = null;
        try {
            huc = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.i("456","2222222");
            e.printStackTrace();
        }
        try {
            huc.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.out.print("33333");
            e.printStackTrace();
        }
        huc.setConnectTimeout(5000);
        huc.setReadTimeout(10000);
        int code = 0;
        try {
            code  = huc.getResponseCode();
            Log.i("11111111",code+"");
        } catch (IOException e) {
            Log.i("456","444444");
            e.printStackTrace();
        }
        InputStream in = null;
        try {
             in  = huc.getInputStream();
        } catch (IOException e) {
            Log.i("456","5555555");
            e.printStackTrace();
        }
        Log.i("456","234567");
        Bitmap b   = BitmapFactory.decodeStream(in);
        String ss  = b.toString();
         Log.i("11111111",ss);
        return b;
    }


}
