package com.quentin.tplbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.quentin.tplbc.models.AdModel;

public class AdViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_view);


        Intent intent = getIntent();

        if(!intent.hasExtra("ad"))
        {
            finishActivity(0);
            return;
        }

        AdModel data = (AdModel) intent.getSerializableExtra("ad");

        ((TextView)findViewById(R.id.adViewTitle)).setText(data.getTitle());

        ((Button) findViewById(R.id.adDataPrice)).setText("Prix : " + data.getPrice() + "€");

    }
}