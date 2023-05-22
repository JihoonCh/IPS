package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ips.R;

public class SubActivity extends AppCompatActivity {

    Button btnCommunity, btnHome, btnMypage;
    Button btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);
        setTitle("메뉴판-음식추천앱");
        // 버튼변수에 버튼객체 대입
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnCommunity = (Button) findViewById(R.id.btnCommunity);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnMypage = (Button) findViewById(R.id.btnMyPage);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 커뮤니티 화면
        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CommunityActivity.class);
                startActivity(intent);
            }
        });
        // 홈화면
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        // 마이페이지 연결
        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SubActivity.class);
                startActivity(intent);
            }
        });
    }
}