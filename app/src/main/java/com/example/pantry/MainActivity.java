package com.example.pantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button addIngredientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIngredientButton = (Button) findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddIngredientActivity();
            }
        });
    }
    public void openAddIngredientActivity(){
        Intent intent = new Intent(this, AddIngredientActivity.class);
        startActivity(intent);
    }
}