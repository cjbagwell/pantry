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

    //Initialize Hard Coded String for Querying Database
    public static final String PANTRY_TABLE = "PANTRY_TABLE";
    public static final String COLUMN_BARCODE = "BARCODE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String COLUMN_QUANTITY_IN_PANTRY = "QUANTITY_IN_PANTRY";
    public static final String COLUMN_CARBS    = "CARBS";
    public static final String COLUMN_FATS     = "COLUMN_FATS";
    public static final String COLUMN_PROTEIN  = "PROTEIN";
    public static final String COLUMN_SUGARS   = "SUGARS";
    public static final String COLUMN_CALORIES = "CALORIES";
    public static final String COLUMN_STORES   = "QUANTITY_IN_PANTRY";
    public static final String COLUMN_BRANDS   = "BRANDS";

    public PantryManager(@Nullable Context context) {
        super(context, "pantry.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to create table
        String createTableStatement = "CREATE TABLE " + PANTRY_TABLE +
                " (" +
                COLUMN_BARCODE + " TEXT PRIMARY KEY, " +
                COLUMN_INGREDIENT_NAME + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_QUANTITY_IN_PANTRY + " INTEGER, " +
                COLUMN_CARBS + " REAL, " +
                COLUMN_BRANDS + " TEXT, " +
                COLUMN_CALORIES + " REAL, " +
                COLUMN_FATS + " REAL, " +
                COLUMN_PROTEIN + " REAL, " +
//                COLUMN_STORES + " TEXT, " +
                COLUMN_SUGARS + " REAL" +
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

    //Updates the quantity of the ingredient in the database
    private boolean updateIngredientQuantity(Ingredient ingredient, int newQuantity) {
        ingredient.setQtInPantry(newQuantity);
        ContentValues cv = contentValuesBuilder(ingredient);
        SQLiteDatabase db = this.getWritableDatabase();
        long update = db.update(PANTRY_TABLE, cv, COLUMN_BARCODE + " = '" + ingredient.getBarcode() + "'", null);
        return update == 1;
    }
    public ContentValues contentValuesBuilder(Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BARCODE,            ingredient.getBarcode());
        cv.put(COLUMN_INGREDIENT_NAME,    ingredient.getName());
        cv.put(COLUMN_IMAGE_URL,          ingredient.getImageUrl());
        cv.put(COLUMN_QUANTITY_IN_PANTRY, ingredient.getQtInPantry());
        cv.put(COLUMN_CARBS,    ingredient.getCarbs());
        cv.put(COLUMN_BRANDS,   ingredient.getBrand());
        cv.put(COLUMN_CALORIES, ingredient.getCalories());
        cv.put(COLUMN_FATS,     ingredient.getFats());
        cv.put(COLUMN_PROTEIN,  ingredient.getProtein());
//        cv.put(COLUMN_STORES, ingredient.getStores()); // TODO: need to implement a way to store the stores into the table
        cv.put(COLUMN_SUGARS,   ingredient.getSugars());
        return cv;
    }

    //Adds an ingredient to the database
    public boolean addToDatabase(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = contentValuesBuilder(ingredient);
        long insert = db.insert(PANTRY_TABLE, null, cv);
        return insert == 1;
    }

    //Retrieves the barcode of an ingredient then deletes it from the database
    public boolean deleteFromDatabase(Ingredient ingredient){
        return deleteFromDatabase(ingredient.getBarcode());
    }

    //Deletes an ingredient from the database based on the barcode
    public boolean deleteFromDatabase(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PANTRY_TABLE + " WHERE " + COLUMN_BARCODE + " = " + barcode;
        Cursor c = db.rawQuery(query, null);
        return c.moveToFirst();
    }

    //Returns the count of the number of ingredients in the database
    public boolean isInDatabase(String barcode){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PANTRY_TABLE + " WHERE " + COLUMN_BARCODE + " = '" + barcode + "'";
        Cursor c = db.rawQuery(query, null);
        return c.getCount() != 0;
    }

    //Returns an ingredient from the database based on the barcode
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

    //Returns all the ingredients with quantity higher than 1 in the database
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

    //Returns information about the ingredient from the cursor using the methods in the Ingredient class
    private Ingredient getIngredientFromCursor(Cursor c){
        int barcodeIndex = c.getColumnIndex(COLUMN_BARCODE);
        int nameIndex = c.getColumnIndex(COLUMN_INGREDIENT_NAME);
        int imageUrlIndex = c.getColumnIndex(COLUMN_IMAGE_URL);
        int quantityInPantryIndex = c.getColumnIndex(COLUMN_QUANTITY_IN_PANTRY);
        int carbsIndex = c.getColumnIndex(COLUMN_CARBS);
        int brandsIndex = c.getColumnIndex(COLUMN_BRANDS);
        int caloriesIndex = c.getColumnIndex(COLUMN_CALORIES);
        int fatsIndex = c.getColumnIndex(COLUMN_FATS);
        int proteinIndex = c.getColumnIndex(COLUMN_PROTEIN);
//        int storesIndex = c.getColumnIndex(COLUMN_STORES);
        int surgarsIndex = c.getColumnIndex(COLUMN_SUGARS);

        Ingredient ingredient = new Ingredient(c.getString(barcodeIndex));
        ingredient.setBrand(c.getString(brandsIndex));
        ingredient.setFats(c.getDouble(fatsIndex));
        ingredient.setCarbs(c.getDouble(carbsIndex));
        ingredient.setImageUrl(c.getString(imageUrlIndex));
        ingredient.setCalories(c.getDouble(caloriesIndex));
//        ingredient.setStores(c.getString(storesIndex));
        ingredient.setQtInPantry(c.getInt(quantityInPantryIndex));
        ingredient.setName(c.getString(nameIndex));
        ingredient.setSugars(c.getDouble(surgarsIndex));
        ingredient.setProtein(c.getDouble(proteinIndex));
        return ingredient;
    }
}
