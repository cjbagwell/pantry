package com.example.pantry;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PantryCommunicator extends AsyncTask<String, Void, Ingredient> {
    private final String API_KEY = "2a3f64a61dd57a26bf473ae4fe69410b";
    private final String API_ID = "3a6ef20f";
    private final String BASE_URL = "https://api.edamam.com/api/food-database";
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
        String parseUrl = this.BASE_URL + "/parser?upc=" + upc[0] +
                "/&app_id=" + API_ID +
                "&app_key=" + API_KEY;
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
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonResponse);
            JSONArray array = jsonObject.getJSONArray("hints");
            JSONObject obj1 = array.getJSONObject(0);
            JSONObject obj2 = obj1.getJSONObject("food");
            String label = (String) obj2.get("label");
            String brand = (String) obj2.get("brand");
            String category = (String) obj2.get("category");
            String image = (String) obj2.get("image");
            String foodContents = (String) obj2.get("foodContentsLabel");
            Ingredient newIngredient = new Ingredient(upc[0], label, image, 0);
            manager.addToDatabase(newIngredient);
            return newIngredient;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Ingredient result){
        super.onPostExecute(result);
        this.pantryCommunicatorCallback.setResult(result);
    }
}
