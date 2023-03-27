package com.example.pantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PantryManager extends SQLiteOpenHelper {
    public static final String PANTRY_TABLE = "PANTRY_TABLE";
    public static final String COLUMN_BARCODE = "BARCODE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String COLUMN_QUANTITY_IN_PANTRY = "QUANTITY_IN_PANTRY";
    public static final String COLUMN_ID = "ID";

    public PantryManager(@Nullable Context context) {
        super(context, "pantry.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PANTRY_TABLE +
                " (" +
                COLUMN_ID + "INTEGER PRIMARY KEY, " +
                COLUMN_BARCODE + " TEXT, " +
                COLUMN_INGREDIENT_NAME + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_QUANTITY_IN_PANTRY + " INTEGER" +
                ")";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean addToPantry(Ingredient ingredient, Integer qtToAdd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, 0); //TODO: make this unique for more ingredients
        cv.put(COLUMN_BARCODE, ingredient.getBarcode());
        cv.put(COLUMN_INGREDIENT_NAME, ingredient.getName());
        cv.put(COLUMN_IMAGE_URL, ingredient.getImageUrl());
        cv.put(COLUMN_QUANTITY_IN_PANTRY, qtToAdd);
        long insert = db.insert(PANTRY_TABLE, null, cv);
        return insert == 1;
    }

    public boolean addToDatabase(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BARCODE, ingredient.getBarcode());
        cv.put(COLUMN_INGREDIENT_NAME, ingredient.getName());
        cv.put(COLUMN_IMAGE_URL, ingredient.getImageUrl());
        cv.put(COLUMN_QUANTITY_IN_PANTRY, ingredient.getQtInPantry());
        long insert = db.insert(PANTRY_TABLE, null, cv);
        return insert == 1;
    }

    public boolean isInDatabase(String barcode){
        return false;
    }

    public Ingredient getIngredient(String barcode){
        String query = "select * from " + PANTRY_TABLE + " where " + COLUMN_BARCODE + "='" + barcode + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(query , null);

        int barcodeIndex = c.getColumnIndex(COLUMN_BARCODE);
        int nameIndex = c.getColumnIndex(COLUMN_INGREDIENT_NAME);
        int imageUrlIndex = c.getColumnIndex(COLUMN_IMAGE_URL);
        int quantityInPantryIndex = c.getColumnIndex(COLUMN_QUANTITY_IN_PANTRY);

        if(c.moveToFirst()){
            Ingredient ingredient = new Ingredient(c.getString(barcodeIndex),
                    c.getString(nameIndex),
                    c.getString(imageUrlIndex),
                    c.getInt(quantityInPantryIndex));
            return ingredient;
        }
        throw new RuntimeException(); // TODO: do something other than just throw an exception with no info
    }
    public boolean isInDatabase(Ingredient ingredient){
        return false;
    }
}
