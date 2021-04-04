package com.example.asyntask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button b1;
    ImageView iv;
    TextView tv;

    String myurl= "https://pixabay.com/api/?key=8907574-f2ba82f0d1e5cef1d06a114e6&q=";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.imagename);
        b1 = findViewById(R.id.submit);
        iv = findViewById(R.id.imageview);
        tv = findViewById(R.id.textview);
        progressBar=findViewById(R.id.progress);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageTask().execute();
            }
        });
    }

    class ImageTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            /*Call your URl*/
            try {
                URL url = new URL(myurl);
                Log.i("mydata",url.toString());
                /*Internet Checking*/
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                /*getting Input*/
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                /*to read starting object to ending object*/
                scanner.useDelimiter("aaa");
                if (scanner.hasNext()){
                    return scanner.next();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            try {
                JSONObject imagedata =new JSONObject(s);
                JSONArray hitArray = imagedata.getJSONArray("hits");
                JSONObject hitObject = hitArray.getJSONObject(15);
                String image =hitObject.getString("LargeImageURL");
                String user = hitObject.getString("User");
                Picasso.get().load(image).into(iv);
                tv.setText(user);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
