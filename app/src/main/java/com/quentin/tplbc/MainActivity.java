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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quentin.tplbc.databinding.ActivityMainBinding;
import com.quentin.tplbc.models.AdModel;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    ArrayList<AdModel> adList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adList = new ArrayList<>();


        adList.add( new AdModel("Titre 1", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 2", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 3", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 4", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 5", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 6", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 7", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 8", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 9", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 10", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 11", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 12", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 13", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 14", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));
        adList.add( new AdModel("Titre 15", "15 rue de la berge 595500 Douai", R.drawable.image1, 15.5));


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




        RecyclerView recyclerView = (RecyclerView) binding.listeAnnonces;
        AdAdapter adapter = new AdAdapter(this,adList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}