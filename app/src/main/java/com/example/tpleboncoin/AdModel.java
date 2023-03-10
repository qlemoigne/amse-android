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

    private String phone;

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
    public AdModel(long _id, String title, String address, String image, double price, String phone)
    {
        this._id = _id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.price = price;
        this.phone = phone;
    }

    /**
     * Constructeur light
     * @param title
     * @param address
     * @param image
     * @param price
     */
    public AdModel(String title, String address, String image, double price, String phone)
    {
        this._id = -1;
        this.title = title;
        this.address = address;
        this.image = image;
        this.price = price;
        this.phone = phone;
    }

    public long getID() {
        return _id;
    }

    public boolean hasID()
    {
        return _id != -1;
    }

    public String getPhone() {
        return phone;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
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
