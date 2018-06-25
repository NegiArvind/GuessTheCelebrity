package com.example.arvind.guesscelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//This below class is for getting html data
public class DownloadTask extends AsyncTask<String,Void,String> {


    @Override
    protected String doInBackground(String... strings) {
        String result=null;
        try{
            URL url=new URL(strings[0]);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            int data=inputStreamReader.read();
            while(data!=-1) {
                char ch = (char) data;
                result += ch;
                data = inputStreamReader.read();
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
