package com.example.arvind.guesscelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebUrls=new ArrayList<>();
    ArrayList<String> celebNames=new ArrayList<>();
    int randomCeleb=0;
    String[] answers=new String[4];
    int correctAnswerPosition=0;
    ImageView mImageView;
    Button button0,button1,button2,button3;


    public void celebChosen(View view){
        if(view.getTag().toString().equals(Integer.toString(correctAnswerPosition))){
            Toast.makeText(getApplicationContext(),"Correct!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong!, it was "+answers[correctAnswerPosition], Toast.LENGTH_LONG).show();
        }
        generateQuestion();
    }

    void generateQuestion() {

        try {

            Random rand = new Random();

            randomCeleb = rand.nextInt(celebUrls.size());

            ImageDownloader imageTask = new ImageDownloader();

            Bitmap celebImage = imageTask.execute(celebUrls.get(randomCeleb)).get();

            mImageView.setImageBitmap(celebImage);

            correctAnswerPosition = rand.nextInt(4);
            int incorrectAnswerPosition;
            for (int i = 0; i < 4; i++) {
                if (i == correctAnswerPosition) {
                    answers[i] = celebNames.get(randomCeleb);
                } else {
                    incorrectAnswerPosition = rand.nextInt(celebUrls.size());
                    while (incorrectAnswerPosition == correctAnswerPosition) {
                        incorrectAnswerPosition = rand.nextInt(celebUrls.size());
                    }
                    answers[i] = celebNames.get(incorrectAnswerPosition);
                }
            }
            //Log.i("mera answer",answers[1]);
            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);
        } catch(Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0=findViewById(R.id.button1);
        button1=findViewById(R.id.button2);
        button2=findViewById(R.id.button3);
        button3=findViewById(R.id.button4);
        mImageView=findViewById(R.id.imageView);


        DownloadTask task=new DownloadTask();
        String result;
        try {

            result=task.execute("http://www.posh24.se/kandisar").get();

                String[] splitResult = result.split("<div class=\"listedArticles\">");

                Pattern pUrls = Pattern.compile("img src=\"(.*?)\"");//it will find a pattern string which starts with
                //img src=" and end with " quotes and we will get the string in between of those starts and ends
                Matcher mUrls = pUrls.matcher(splitResult[0]);//mathcher will find the pattern in splitResult[0] string
                while (mUrls.find()) {

                    celebUrls.add(mUrls.group(1));
                    //Log.i("url",mUrls.group(1));

                }

                Pattern pNames = Pattern.compile("alt=\"(.*?)\"");
                Matcher mNames = pNames.matcher(splitResult[0]);
                while (mNames.find()) {

                    celebNames.add(mNames.group(1));// it will add the name comes in mNames in arraylist
                    //Log.i("names",mNames.group(1));
                }
                generateQuestion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
