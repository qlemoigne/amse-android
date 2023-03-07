package com.quentin.tplbc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.quentin.tplbc.models.AdModel;

public class DBManager {

    public static DBManager DBManager;

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    private DBManager(Context c) {
        context = c;

        if(fetch().getCount() == 0)
        {
            init();
        }
        //init(); // Useful for adding ads for the first time.
    }

    public static DBManager getDBManager(Context context) {
        if (DBManager == null){
            return new DBManager(context);
        }
        return DBManager;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Add ads manually.
    public void init(){
        open();
        
        insert(new AdModel("Wood", "Douai", "https://media.istockphoto.com/id/134253640/photo/construction-of-a-wooden-roof-frame-underway.jpg?s=612x612&w=0&k=20&c=e5gUkic9LGQWahIdHozOsEzHKy_HtsmvmtOHmYsejSU=", 30.0));
        insert(new AdModel("Steel", "Lille", "https://as2.ftcdn.net/v2/jpg/03/91/83/87/1000_F_391838708_4HFADW5beay2VVlnoual6Qi5fWeIaD9V.jpg", 22.4));
        insert(new AdModel("Clay", "Douai", "https://constrofacilitator.com/wp-content/uploads/2020/02/clay-in-construction.jpg", 30940.4));
        insert(new AdModel("Metal", "Lyon", "https://www.meto-constructions.fr/wp-content/uploads/2018/12/IMG_6067.jpg", 10302.4));
        insert(new AdModel("Glass", "Valenciennes", "https://i0.wp.com/www.tipsnepal.com/wp-content/uploads/2020/09/simple-float-glass-1505049573-3306125.jpeg?resize=500%2C317&quality=100&strip=all&ssl=1", 2340.4));
        insert(new AdModel("Wood", "Orchies", "https://yieldpro.com/wp-content/uploads/2020/08/lumber1.jpg", 210.4));
        insert(new AdModel("Wood", "Roubaix", "https://media.istockphoto.com/id/134253640/photo/construction-of-a-wooden-roof-frame-underway.jpg?s=612x612&w=0&k=20&c=e5gUkic9LGQWahIdHozOsEzHKy_HtsmvmtOHmYsejSU=", 220.4));
        insert(new AdModel("Steel", "Marseille", "https://as2.ftcdn.net/v2/jpg/03/91/83/87/1000_F_391838708_4HFADW5beay2VVlnoual6Qi5fWeIaD9V.jpg", 720.4));
        insert(new AdModel("Clay", "Valence", "https://constrofacilitator.com/wp-content/uploads/2020/02/clay-in-construction.jpg", 820.4));
        insert(new AdModel("Metal", "Paris", "https://www.meto-constructions.fr/wp-content/uploads/2018/12/IMG_6067.jpg", 2320.4));
        insert(new AdModel("Glass", "Strasbourg", "https://i0.wp.com/www.tipsnepal.com/wp-content/uploads/2020/09/simple-float-glass-1505049573-3306125.jpeg?resize=500%2C317&quality=100&strip=all&ssl=1", 20.4));
        insert(new AdModel("Wood", "Bordeaux", "https://yieldpro.com/wp-content/uploads/2020/08/lumber1.jpg", 2560.54));


    }

    public void insert(AdModel ad) {

        if(dbHelper == null)
        {
            open();
        }

        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.TITLE, ad.getTitle());
        contentValue.put(DBHelper.ADDRESS, ad.getAddress());
        contentValue.put(DBHelper.IMAGE, ad.getImage());
        contentValue.put(DBHelper.PRICE, ad.getPrice());
        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {

        if(dbHelper == null)
        {
            open();
        }

        String[] columns = new String[] { DBHelper._ID, DBHelper.TITLE, DBHelper.ADDRESS, DBHelper.IMAGE, DBHelper.PRICE};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, AdModel ad) {

        if(dbHelper == null)
        {
            open();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TITLE, ad.getTitle());
        contentValues.put(DBHelper.ADDRESS, ad.getAddress());
        contentValues.put(DBHelper.IMAGE, ad.getImage());
        int i = database.update(DBHelper.TABLE_NAME, contentValues, DBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {

        if(dbHelper == null)
        {
            open();
        }

        database.delete(DBHelper.TABLE_NAME, DBHelper._ID + "=" + _id, null);
    }



    public AdModel getById(int id){

        if(dbHelper == null)
        {
            open();
        }

        return dbHelper.getById(id);
    }

}