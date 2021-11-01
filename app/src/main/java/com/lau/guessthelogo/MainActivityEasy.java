package com.lau.guessthelogo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.widget.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.view.*;

public class MainActivityEasy extends AppCompatActivity {

    ImageView img;
    Button btn1, btn2, btn3, btn4;

    int x;
    String result;

    ArrayList<String> names;
    ArrayList<String> pics;
    ArrayList<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_easy);

        img = (ImageView) findViewById(R.id.easylogo);
        btn1 = (Button) findViewById(R.id.easybtn1);
        btn2 = (Button) findViewById(R.id.easybtn2);
        btn3 = (Button) findViewById(R.id.easybtn3);
        btn4 = (Button) findViewById(R.id.easybtn4);

        names = new ArrayList<String>();
        pics = new ArrayList<String>();
        options =  new ArrayList<String>();

        x=0; result= "";

        //images and info were not loading due to a threadException, and this is how google told us to solve the issue :)
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        try {
            URL pcmag = new URL("https://www.pcmag.com/picks/best-android-apps");
            BufferedReader br = new BufferedReader(new InputStreamReader(pcmag.openStream()));
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            br.close();

            String str = stringBuffer.toString();
            //Regex to get the image URL
            String regex = "http(s?)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./]*)+\\.(?:[gG][iI][fF]|[jJ][pP][gG]|[jJ][pP][eE][gG]|[pP][nN][gG]|[bB][mM][pP])";

            Matcher matcher = Pattern.compile(regex).matcher(str);
            String r = "";

            while (matcher.find()) {
                String finder = matcher.group();
                int start = finder.indexOf("src=") + 1;
                String match = finder.substring(start, finder.length());
                r+= match+"\n";
                pics.add(match);
            }
            //Regex to get the name of the application
            String headerRegex = "<h2 class=\"order-last md:order-first font-bold font-brand text-lg md:text-xl leading-normal w-full\">(.*?)</h2>";
            Matcher matcher2 = Pattern.compile(headerRegex).matcher(str);

            int count = 0;

            while(matcher2.find()){
                String finder = matcher2.group();
                int start = finder.indexOf(">") + 1;
                int end = finder.indexOf("</h2>");
                String match = finder.substring(start, end);
                names.add(match);
            }
            displayChoices();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap downloadImage(String s) {
        try {
            URL u = new URL(s);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setDoInput(true); c.connect();
            InputStream in = c.getInputStream();
            Bitmap bit = BitmapFactory.decodeStream(in);
            return bit;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void displayChoices(){

        x++;
        Random gen = new Random();
        int a = gen.nextInt(names.size()) + 12;
        int b = a - 12;

        img.setImageBitmap(downloadImage(pics.get(a)));
        result = names.get(b);
        options.add(result);

        while(options.size()<4){
            int any = gen.nextInt(names.size());
            if(!names.get(any).equalsIgnoreCase(result)) options.add(names.get(any));
        }
        //Randomly distributing the choices to the 4 buttons
        int any = gen.nextInt(options.size());

        btn1.setText(options.remove(any));

        any = gen.nextInt(options.size());
        btn2.setText(options.remove(any));

        any = gen.nextInt(options.size());
        btn3.setText(options.remove(any));

        any = gen.nextInt(options.size());
        btn4.setText(options.remove(any));

    }
    public void easyClick(View view) {
        Button bt = (Button) view;
        if(bt.getText().toString().equalsIgnoreCase(result)) {
            Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
            displayChoices();
        }
        else Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
    }
}


