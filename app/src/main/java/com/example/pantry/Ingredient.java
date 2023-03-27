package com.example.pantry;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Ingredient {
    private String barcode;
    private String name;
    private String imageUrl;
//    private Bitmap image;
    private int qtInPantry;

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

    public void setImage(String image) {
        this.imageUrl = image;
    }

    public int getQtInPantry() {
        return qtInPantry;
    }

    public void setQtInPantry(int qtInPantry) {
        this.qtInPantry = qtInPantry;
    }
}
