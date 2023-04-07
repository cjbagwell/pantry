package com.example.pantry;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedList;

public class Ingredient {
    protected double sugars;
    protected String barcode;
    protected String name;
    protected String imageUrl;
//    private Bitmap image;
    protected int qtInPantry;
    protected double carbs;
    protected double fats;
    protected double protein;
    protected double calories;
    protected LinkedList<String> stores;
    protected String brand;

    public Ingredient(String barcode, String name, String imageUrl,
                      int qtInPantry, double carbs, double sugars,
                      double fats, double protein, double calories,
                      LinkedList<String> stores, String brand) {
        this.barcode = barcode;
        this.name = name;
        this.imageUrl = imageUrl;
        this.qtInPantry = qtInPantry;
        this.carbs = carbs;
        this.sugars = sugars;
        this.fats = fats;
        this.protein = protein;
        this.calories = calories;
        this.stores = stores;
        this.brand = brand;
    }
    public Ingredient(String barcode){
        this(barcode, "Unknown", "", 0, -1, -1, -1, -1, -1, null, "Unknown");
        stores = new LinkedList<String>();
        stores.add("Unknown");
    }
    Ingredient(String bar, String nam, String imageUrl, int qtInPan){
        this.barcode = bar;
        this.name = nam;
        this.imageUrl = imageUrl;
        this.qtInPantry = qtInPan;
    }

    // To String Method

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", qtInPantry=" + qtInPantry +
                '}';
    }

    public void loadImageIntoImageView(Context context, ImageView imageView){
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(context)
                        .load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
            }
        });

    }

    // ********************* GETTERS AND SETTERS *****************************
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public double getCarbs() {
        return carbs;
    }
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
    public double getFats() {
        return fats;
    }
    public void setFats(double fats) {
        this.fats = fats;
    }
    public double getProtein() {
        return protein;
    }
    public void setProtein(double protein) {
        this.protein = protein;
    }
    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }
    public LinkedList<String> getStores() {
        return stores;
    }
    public void setStores(LinkedList<String> stores) {
        stores = stores;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setImage(String image) {
        this.imageUrl = image;
    }
    public int getQtInPantry() {
        return qtInPantry;
    }
    public void setQtInPantry(int qtInPantry) {
        this.qtInPantry = qtInPantry;
    }
    public double getSugars() {
        return sugars;
    }
    public void setSugars(double sugars) {
        this.sugars = sugars;
    }
}
