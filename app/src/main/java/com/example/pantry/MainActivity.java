package com.example.pantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button addIngredientButton;
private Button removeIngredientButton;
private Button viewPantryButton;
private Button refillPantryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIngredientButton     = (Button) findViewById(R.id.addIngredientButton);
        removeIngredientButton  = (Button) findViewById(R.id.removeIngredientButton);
        viewPantryButton        = (Button) findViewById(R.id.viewPantryButton);
        refillPantryButton      = (Button) findViewById(R.id.refillPantryButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddIngredientActivity();
            }
        });

        removeIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRemoveIngredientActivity();
            }
        });

        viewPantryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewPantryActivity();
            }
        });

        refillPantryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRefillPantryActivity();
            }
        });


    }
    public void openAddIngredientActivity(){
        Intent intent = new Intent(this, AddIngredientActivity.class);
        startActivity(intent);
    }
    public void openRemoveIngredientActivity(){
        Intent intent = new Intent(this, RemoveIngredientActivity.class);
        startActivity(intent);
    }
    public void openViewPantryActivity(){
        Intent intent = new Intent(this, ViewPantryActivity.class);
        startActivity(intent);
    }
    public void openRefillPantryActivity(){
        Intent intent = new Intent(this, RefillPantryActivity.class);
        startActivity(intent);
    }
}