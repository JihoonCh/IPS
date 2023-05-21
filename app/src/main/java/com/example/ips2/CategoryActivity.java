package com.example.ips2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnKorean, btnWestern, btnJapanese, btnChinese, btnCafe, btnBakery;
    Button btnNext, btnHome;
    Button lastClickedButton;
    String category;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnKorean = (Button) findViewById(R.id.korean);
        btnWestern = (Button) findViewById(R.id.western);
        btnJapanese = (Button) findViewById(R.id.japanese);
        btnChinese = (Button) findViewById(R.id.chinese);
        btnCafe = (Button) findViewById(R.id.cafe);
        btnBakery = (Button) findViewById(R.id.bakery);

        btnNext = (Button) findViewById(R.id.next_fromEat);
        btnHome = (Button) findViewById(R.id.home_fromEat);

        btnKorean.setOnClickListener(this);
        btnWestern.setOnClickListener(this);
        btnJapanese.setOnClickListener(this);
        btnChinese.setOnClickListener(this);
        btnCafe.setOnClickListener(this);
        btnBakery.setOnClickListener(this);

        btnNext = findViewById(R.id.next_fromEat);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CategoryActivity.this, AddressActivity.class);
                //intent.putExtra("Food Category", category);
                //startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if (lastClickedButton != null) {
            lastClickedButton.setBackgroundColor(Color.LTGRAY);
        }
        clickedButton.setBackgroundColor(Color.YELLOW);
        lastClickedButton = clickedButton;
        if (v != btnNext && v != btnHome) category = clickedButton.getText().toString();
    }
}