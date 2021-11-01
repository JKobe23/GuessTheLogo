package com.lau.guessthelogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goEasy(View view) {
        Intent i1 = new Intent(getApplicationContext(), MainActivityEasy.class);
        startActivity(i1);
    }

    public void goNeutral(View view) {
        Intent i2 = new Intent(getApplicationContext(), MainActivityNeutral.class);
        startActivity(i2);

    }
    public void goHard(View view) {
        Intent i3 = new Intent(getApplicationContext(), MainActivityHard.class);
        startActivity(i3);
    }

}