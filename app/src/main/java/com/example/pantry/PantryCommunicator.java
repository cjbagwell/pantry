package com.example.pantry;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PantryCommunicator extends AsyncTask<String, Void, Ingredient> {
    //private final String API_KEY = "2a3f64a61dd57a26bf473ae4fe69410b";
    //private final String API_ID = "3a6ef20f";
    private final String BASE_URL = "https://world.openfoodfacts.org/api/v0/product/";
    private PantryCommunicatorCallback pantryCommunicatorCallback;
    private Context ctx;

    void setInstance(Context context){
        pantryCommunicatorCallback = (PantryCommunicatorCallback) context;
        this.ctx = context;
    }

    @Override
    protected Ingredient doInBackground(String... upc) {
        PantryManager manager = new PantryManager(this.ctx);
        if(manager.isInDatabase(upc[0])){
            return manager.getIngredient(upc[0]);
        }
        //else get ingredient from API call to Edamam
        String parseUrl = this.BASE_URL + upc[0] + ".json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(parseUrl)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String jsonResponse = null;
        try {
            jsonResponse = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Check if the product was found
        JSONObject jsonObject = null;
        if(jsonResponse.contains("product not found")){
            return null;
        }

        // Create Default ingredient
        Ingredient newIngredient = new Ingredient(upc[0]);

        // Parse the Json Object
        try {
            jsonObject = new JSONObject(jsonResponse);
            JSONObject product = jsonObject.getJSONObject("product");
            String states = product.getString("states");
            if(states.contains("product-name-completed")){
                newIngredient.setName(product.getString("product_name"));
            }
            if(states.contains("brands-completed")){
                newIngredient.setBrand(product.getString("brands"));
            }
            if(states.contains("front-photo-selected")) {
                newIngredient.setImageUrl(product.getString("image_front_url"));
            }
            if(states.contains("nutrition-facts-completed")) {
                JSONObject nutriments = product.getJSONObject("nutriments");
                newIngredient.setCarbs(   nutriments.getDouble("carbohydrates_100g"));
                newIngredient.setProtein( nutriments.getDouble("proteins_100g"));
                newIngredient.setFats(    nutriments.getDouble("fat_100g"));
                newIngredient.setCalories(nutriments.getDouble("energy-kcal_100g"));
                newIngredient.setSugars(  nutriments.getDouble("sugars_100g"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        manager.addToDatabase(newIngredient);
        return newIngredient;
    }

    @Override
    protected void onPostExecute(Ingredient result){
        super.onPostExecute(result);
        this.pantryCommunicatorCallback.setResult(result);
    }
}
