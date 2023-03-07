package com.quentin.tplbc;

import static com.quentin.tplbc.DBHelper.ADDRESS;
import static com.quentin.tplbc.DBHelper.IMAGE;
import static com.quentin.tplbc.DBHelper.PRICE;
import static com.quentin.tplbc.DBHelper.TITLE;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    ArrayList<AdModel> adList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Intent intent = getIntent();

        if(intent != null)
        {
            if(intent.hasExtra("newAdTittle"))
            {
                Snackbar.make(binding.getRoot(), "L'annonce " + intent.getStringExtra("newAdTittle") + " a été créé succès !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }


     /* adList.add( new AdModel("Titre 10", "15 rue de la berge 595500 Douai", "", 15.5));
        adList.add( new AdModel("Titre 11", "15 rue de la berge 595500 Douai", "", 15.5));
        adList.add( new AdModel("Titre 12", "15 rue de la berge 595500 Douai","", 15.5));
        adList.add( new AdModel("Titre 13", "15 rue de la berge 595500 Douai", "", 15.5));
        adList.add( new AdModel("Titre 14", "15 rue de la berge 595500 Douai", "", 15.5));
        adList.add( new AdModel("Titre 15", "15 rue de la berge 595500 Douai","", 15.5));
*/



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(MainActivity.this, CreateAdActivity.class);
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();

        updateAdList();
    }

    public void updateAdList()
    {



        ArrayList<AdModel> adList = new ArrayList<>();

        Cursor cursor = DBManager.getDBManager(this).fetch();

        if(cursor == null)
        {
            return;
        }

       // DBManager.getDBManager(this).init();
        while(cursor.moveToNext())
        {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE));



            adList.add(new AdModel(title, address, image, price));
        }

        RecyclerView recyclerView = (RecyclerView) binding.listeAnnonces;
        AdAdapter adapter = new AdAdapter(this,adList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }
}