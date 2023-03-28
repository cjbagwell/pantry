package com.example.pantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PantryManager extends SQLiteOpenHelper {
    public static final String PANTRY_TABLE = "PANTRY_TABLE";
    public static final String COLUMN_BARCODE = "BARCODE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String COLUMN_QUANTITY_IN_PANTRY = "QUANTITY_IN_PANTRY";

    public PantryManager(@Nullable Context context) {
        super(context, "pantry.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PANTRY_TABLE +
                " (" +
                COLUMN_BARCODE + " TEXT PRIMARY KEY, " +
                COLUMN_INGREDIENT_NAME + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_QUANTITY_IN_PANTRY + " INTEGER" +
                ")";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean addToPantry(String barcode, Integer qtToAdd){
        return addToPantry(getIngredient(barcode), qtToAdd);
    }

    public boolean addToPantry(Ingredient ingredient, Integer qtToAdd){
        if(!isInDatabase(ingredient)){
            if(!addToDatabase(ingredient)) {
                throw new RuntimeException(); //TODO: add some more meaningful messages
            }
        }
        Ingredient oldIngredient = getIngredient(ingredient.getBarcode());
        int newQty = oldIngredient.getQtInPantry() + qtToAdd;
        return updateIngredientQuantity(ingredient, newQty);
    }
    private boolean updateIngredientQuantity(Ingredient ingredient, int newQuantity) {
        ingredient.setQtInPantry(newQuantity);
        ContentValues cv = contentValuesBuilder(ingredient);
        SQLiteDatabase db = this.getWritableDatabase();
        long update = db.update(PANTRY_TABLE, cv, COLUMN_BARCODE + " = '" + ingredient.getBarcode() + "'", null);
        return update == 1;
    }
    public ContentValues contentValuesBuilder(Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BARCODE, ingredient.getBarcode());
        cv.put(COLUMN_INGREDIENT_NAME, ingredient.getName());
        cv.put(COLUMN_IMAGE_URL, ingredient.getImageUrl());
        cv.put(COLUMN_QUANTITY_IN_PANTRY, ingredient.getQtInPantry());
        return cv;
    }

    public boolean addToDatabase(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = contentValuesBuilder(ingredient);
        long insert = db.insert(PANTRY_TABLE, null, cv);
        return insert == 1;
    }

    public boolean deleteFromDatabase(Ingredient ingredient){
        return deleteFromDatabase(ingredient.getBarcode());
    }

    public boolean deleteFromDatabase(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PANTRY_TABLE + " WHERE " + COLUMN_BARCODE + " = " + barcode;
        Cursor c = db.rawQuery(query, null);
        return c.moveToFirst();
    }

    public boolean isInDatabase(String barcode){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PANTRY_TABLE + " WHERE " + COLUMN_BARCODE + " = '" + barcode + "'";
        Cursor c = db.rawQuery(query, null);
        return c.getCount() != 0;
    }

    public Ingredient getIngredient(String barcode){
        String query = "select * from " + PANTRY_TABLE + " where " + COLUMN_BARCODE + "='" + barcode + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery(query , null);
        if(c.moveToFirst()){
            return getIngredientFromCursor(c);
        }
        throw new RuntimeException(); // TODO: do something other than just throw an exception with no info
    }
    public boolean isInDatabase(Ingredient ingredient){
        return isInDatabase(ingredient.getBarcode());
    }

    public ArrayList<Ingredient> getAllPantryIngredients(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PANTRY_TABLE + " WHERE " + COLUMN_QUANTITY_IN_PANTRY + " > 0";
        Cursor cursor =  db.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<Ingredient> ingredients = new ArrayList<>(cursor.getCount());
        do{
            if(!ingredients.add(getIngredientFromCursor(cursor))){
                throw new RuntimeException(); //TODO: Do something more meaningful here
            };
        } while(cursor.moveToNext());
        return ingredients;
    }

    private Ingredient getIngredientFromCursor(Cursor c){
        int barcodeIndex = c.getColumnIndex(COLUMN_BARCODE);
        int nameIndex = c.getColumnIndex(COLUMN_INGREDIENT_NAME);
        int imageUrlIndex = c.getColumnIndex(COLUMN_IMAGE_URL);
        int quantityInPantryIndex = c.getColumnIndex(COLUMN_QUANTITY_IN_PANTRY);

        return new Ingredient(c.getString(barcodeIndex),
                c.getString(nameIndex),
                c.getString(imageUrlIndex),
                c.getInt(quantityInPantryIndex));
    }
}
