package com.example.tpleboncoin;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class AdModel implements Serializable {

    private long _id;
    private String title;
    private String address;
    private String image;

    private double price;

    @Nullable
    private Bitmap cache;

    /**
     * Constructeur complet
     * @param _id
     * @param title
     * @param address
     * @param image
     * @param price
     */
    public AdModel(long _id, String title, String address, String image, double price)
    {
        this._id = _id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.price = price;
    }

    /**
     * Constructeur light
     * @param title
     * @param address
     * @param image
     * @param price
     */
    public AdModel(String title, String address, String image, double price)
    {
        this._id = -1;
        this.title = title;
        this.address = address;
        this.image = image;
        this.price = price;
    }

    public long getID() {
        return _id;
    }

    public boolean hasID()
    {
        return _id != -1;
    }


    public boolean isImageLoaded()
    {
        return cache != null;
    }

    public Bitmap getCachedImage()
    {
        return cache;
    }

    public void setCachedImage(@Nullable Bitmap cache) {
        this.cache = cache;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public boolean isInvalidImage() {
        return image == null || image.length() <= 0;
    }

    public void invalidateCache()
    {
        cache = null;
    }
    public boolean isLocal() {

        if(isInvalidImage()) {
            return false;
        }

        return image.startsWith("internal");

    }
}
