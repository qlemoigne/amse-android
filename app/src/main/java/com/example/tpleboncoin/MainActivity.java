package com.example.tpleboncoin;

import static com.example.tpleboncoin.DBHelper.ADDRESS;
import static com.example.tpleboncoin.DBHelper.IMAGE;
import static com.example.tpleboncoin.DBHelper.PHONE;
import static com.example.tpleboncoin.DBHelper.PRICE;
import static com.example.tpleboncoin.DBHelper.TITLE;
import static com.example.tpleboncoin.DBHelper._ID;

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

import com.example.tpleboncoin.databinding.ActivityMainBinding;
import com.example.tpleboncoin.AdModel;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    public static File CACHE_DIR;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    ArrayList<AdModel> adList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CACHE_DIR = getApplication().getCacheDir();


        Intent intent = getIntent();

        if(intent != null)
        {
            if(intent.hasExtra("newAdTittle"))
            {
                Snackbar.make(binding.getRoot(), "L'annonce " + intent.getStringExtra("newAdTittle") + " a été créé succès !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            if(intent.hasExtra("updatedAdTittle"))
            {
                Snackbar.make(binding.getRoot(), "L'annonce " + intent.getStringExtra("updatedAdTittle") + " a été modifié avec succès !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }


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

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void updateAdList()
    {
        if(adList.size() > 0)
        {
            adList.clear();
        }


        Cursor cursor = DBManager.getDBManager(this).fetch();

        if(cursor == null)
        {
            return;
        }

        // DBManager.getDBManager(this).init();
        while(cursor.moveToNext())
        {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(PHONE));


            adList.add(new AdModel(id, title, address, image, price, phone));
        }

        RecyclerView recyclerView = (RecyclerView) binding.listeAnnonces;
        AdAdapter adapter = new AdAdapter(this, adList) {
            @Override
            public void onAdRemoved() {
                updateAdList();
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }
}