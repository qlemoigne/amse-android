package com.example.tpleboncoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.example.tpleboncoin.AdModel;

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
        ((TextView) findViewById(R.id.dataPriceText)).setText(data.getPrice() + "€");
        ((TextView) findViewById(R.id.dataAddressText)).setText(data.getAddress());

        ShapeableImageView imageView = findViewById(R.id.adViewImage);

        if(data.isInvalidImage())
        {
            imageView.setImageResource(R.drawable.image0);

        } else {
            if(data.isLocal()) {
                imageView.setImageURI(Uri.parse(MainActivity.CACHE_DIR + "/" + data.getImage()));
            } else {

                DownloadImageTask task = new DownloadImageTask() {
                    @Override
                    protected Bitmap doInBackground(String... urls) {
                        return super.doInBackground(urls);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {

                        data.setCachedImage(result);
                        imageView.setImageBitmap(result);


                    }
                };

                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data.getImage());

            }
        }

        Button button = findViewById(R.id.adDataPrice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "L'achat a été réalisé avec succès !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }
}
