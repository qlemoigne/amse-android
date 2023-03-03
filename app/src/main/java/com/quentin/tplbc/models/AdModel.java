package com.quentin.tplbc.models;

import java.io.Serializable;

/**
 *
 */
public class AdModel implements Serializable {

    private String title;
    private String address;
    private int image;

    /**
     *
     * @param title
     * @param address
     * @param image
     */
    public AdModel(String title, String address, int image)
    {
        this.title = title;
        this.address = address;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }
}
