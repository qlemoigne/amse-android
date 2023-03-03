package com.quentin.tplbc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.quentin.tplbc.databinding.ActivityMainBinding;
import com.quentin.tplbc.models.AdModel;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    AdModel[] adList = new AdModel[] {

            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),
            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1),

            new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(MainActivity.this, CreateAdActivity.class);
                startActivity(intent);
            }
        });

        updateAdList();



    }

    public void updateAdList()
    {
        LinearLayout container = binding.listeAnnonces;

        container.removeAllViews();

        for(AdModel adModel : adList)
        {
            View view = getLayoutInflater().inflate(R.layout.ad_fragment, container, false);

            ((TextView) view.findViewById(R.id.adTitle)).setText(adModel.getTitle());
            ((TextView) view.findViewById(R.id.adAddress)).setText(adModel.getAddress());
            ((ImageView) view.findViewById(R.id.adImage)).setImageDrawable(getDrawable(adModel.getImage()));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AdViewActivity.class);
                    intent.putExtra("ad", adModel);

                    startActivity(intent);
                }
            });

            container.addView(view);
        }
    }
}