package com.shaan.retrofittutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class CaloriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        (findViewById(R.id.bFruits)).setOnClickListener(event -> {
            Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
            intent.putExtra("type","fruits");
            startActivity(intent);
        });

        (findViewById(R.id.bVegetables)).setOnClickListener(event -> {
            Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
            intent.putExtra("type","vegetables");
            startActivity(intent);
        });

    }
}
