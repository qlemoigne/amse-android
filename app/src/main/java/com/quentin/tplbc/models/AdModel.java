package com.quentin.tplbc.models;

import java.io.Serializable;

/**
 *
 */
public class AdModel implements Serializable {

    private String title;
    private String address;
    private String image;

    private double price;

    /**
     *
     * @param title
     * @param address
     * @param image
     * @param price
     */
    public AdModel(String title, String address, String image, double price)
    {
        this.title = title;
        this.address = address;
        this.image = image;
        this.price = price;
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
}
