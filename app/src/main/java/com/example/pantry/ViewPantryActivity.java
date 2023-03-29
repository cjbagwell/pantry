package com.example.pantry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewPantryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private IngredientAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Ingredient> pantry;
    ImageButton minusIngredientButton;
    ImageButton plusIngredientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pantry);

        minusIngredientButton = findViewById(R.id.imageButtonMinus);
        plusIngredientButton = findViewById(R.id.imageButtonPlus);


        Intent intent = getIntent();
        String pantryJsonStringArray = null;

        PantryManager manager = new PantryManager(this);
        pantry = manager.getAllPantryIngredients();

        if(pantry.size() == 0){
            Toast.makeText(this, "Your pantry is empty!", Toast.LENGTH_LONG).show();
        }
        buildRecyclerView();
    }

    public void changeIngredientName(int position, String text) {
        pantry.get(position).setName(text);
        mAdapter.notifyItemChanged(position);
    }


    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new IngredientAdapter(pantry);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeIngredientName(position, "Clicked");
            }
        });
    }
//
//    @Override
//    public void onBackPressed() {
//        String pantryJsonArray = new Gson().toJson(pantry);
//        System.out.println("ViewPantry onBackPressed says: " + pantryJsonArray);
//        Intent intent = new Intent(ViewPantryActivity.this, MainActivity.class);
//        intent.putExtra("newPantryContents", pantryJsonArray);
//        setResult(RESULT_OK, intent);
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onDestroy() {
//        String pantryJsonArray = new Gson().toJson(pantry);
//        System.out.println("ViewPantry onDestroy says: " + pantryJsonArray);
//        Intent intent = new Intent(ViewPantryActivity.this, MainActivity.class);
//        intent.putExtra("newPantryContents", pantryJsonArray);
//        setResult(RESULT_OK, intent);
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onPause() {
//        String pantryJsonArray = new Gson().toJson(pantry);
//        System.out.println("ViewPantry onPause Says" + pantryJsonArray);
//        Intent resultIntent = new Intent(ViewPantryActivity.this, MainActivity.class);
//        resultIntent.putExtra("newPantryContents", pantryJsonArray);
//        setResult(ViewPantryActivity.RESULT_OK, resultIntent);
//        super.onPause();
//
//    }
}