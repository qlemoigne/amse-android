package com.example.tpleboncoin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tpleboncoin.AdModel;

public class DBHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "ad_list";

    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image";

    public static final String PRICE = "price";

    public static final String PHONE = "phone";

    // Database Information
    static final String DB_NAME = "LEBONCOIN.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + ADDRESS + " TEXT, " + IMAGE + " TEXT, " + PRICE+ " REAL, " + PHONE +" TEXT);";

    public DBHelper(Context context) {
        super(context,
                DB_NAME,
                null,
                DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Util if you want to add a clicklistener on specific ad in listview.
    public AdModel getById(long id) {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" where "+ _ID + "=?";
        Cursor data = db.rawQuery(query,new String[] {String.valueOf(id)});
        if (data != null) {
            data.moveToFirst();
        }
        else{
            return null;
        }

        String title = data.getString(data.getColumnIndexOrThrow(TITLE));
        String address = data.getString(data.getColumnIndexOrThrow(ADDRESS));
        String image = data.getString(data.getColumnIndexOrThrow(IMAGE));
        double price = data.getDouble(data.getColumnIndexOrThrow(PRICE));
        String phone = data.getString(data.getColumnIndexOrThrow(PHONE));

        return new AdModel(title, address, image, price, phone);
    }
}
