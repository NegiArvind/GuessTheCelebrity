package com.example.arvind.guesscelebrity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//This below class is for downloading images
public class ImageDownloader extends AsyncTask<String,Void,Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {
        try{
            URL url=new URL(strings[0]);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();

            Bitmap myBitmap= BitmapFactory.decodeStream(inputStream);

            return myBitmap;
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}