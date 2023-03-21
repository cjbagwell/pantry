package com.example.pantry;

import android.graphics.Bitmap;
import com.google.android.gms.vision.barcode.Barcode;

public class Ingredient {
    Barcode barcode;
    String name;
    Bitmap image;
    int qtInPantry;

    Ingredient(Barcode bar, String nam, Bitmap im, int qtInPan){
        this.barcode = bar;
        this.name = nam;
        this.image = im;
        this.qtInPantry = qtInPan;
    }
}
