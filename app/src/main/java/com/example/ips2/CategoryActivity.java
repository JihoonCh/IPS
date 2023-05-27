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
    Button btnNext, btnHome, btnBack;
    Button lastClickedButton;
    String category;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        String user_address = intent.getStringExtra("User Address");

        btnKorean = (Button) findViewById(R.id.korean);
        btnWestern = (Button) findViewById(R.id.western);
        btnJapanese = (Button) findViewById(R.id.japanese);
        btnChinese = (Button) findViewById(R.id.chinese);
        btnCafe = (Button) findViewById(R.id.cafe);
        btnBakery = (Button) findViewById(R.id.bakery);

        btnNext = (Button) findViewById(R.id.next_fromEat);
        btnHome = (Button) findViewById(R.id.home_fromEat);
        btnBack = (Button) findViewById(R.id.back_fromEat);

        btnKorean.setOnClickListener(this);
        btnWestern.setOnClickListener(this);
        btnJapanese.setOnClickListener(this);
        btnChinese.setOnClickListener(this);
        btnCafe.setOnClickListener(this);
        btnBakery.setOnClickListener(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //어떤 카테고리 선택했는지 식당액티비티에 전달하며 호출함
                Intent intent = new Intent(CategoryActivity.this, RestaurantActivity.class);
                intent.putExtra("Food Category", category);
                intent.putExtra("User Address", user_address);
                startActivity(intent);
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, GuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //클릭한 버튼 색상 변경 효과
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if (lastClickedButton != null) {
            lastClickedButton.setBackgroundColor(Color.parseColor("#FDCFBB"));
        }
        clickedButton.setBackgroundColor(Color.parseColor("#BB86FC"));
        lastClickedButton = clickedButton;
        if (v != btnNext && v != btnHome) category = clickedButton.getText().toString();
    }
}