package nl.hs_hague.restaurant.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by vural on 16.09.16.
 */
public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String description;
    private String place;
    private String street;
    private String zip;
    private Bitmap image;


    public Restaurant(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Restaurant(int id, String name, String description, String place, String street, String zip, Bitmap image){
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.street = street;
        this.zip = zip;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
