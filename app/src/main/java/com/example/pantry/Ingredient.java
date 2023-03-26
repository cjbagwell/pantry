package com.example.pantry;

import android.graphics.Bitmap;
import com.google.android.gms.vision.barcode.Barcode;

public class Ingredient {
    private Barcode barcode;
    private String name;
    private String image;
    private int qtInPantry;

    Ingredient(Barcode bar, String nam, String im, int qtInPan){
        this.barcode = bar;
        this.name = nam;
        this.image = im;
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


    // ********************* GETTERS AND SETTERS *****************************

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQtInPantry() {
        return qtInPantry;
    }

    public void setQtInPantry(int qtInPantry) {
        this.qtInPantry = qtInPantry;
    }
}
