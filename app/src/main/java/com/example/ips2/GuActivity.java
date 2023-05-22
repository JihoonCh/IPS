package com.example.ips2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnSeongdong, btnMapo, btnJongno, btnGwanak, btnJungnang, btnGeumcheon;
    Button btnGwangjin, btnNowon, btnJung, btnSeongbuk, btnSeodaemun, btnSeocho;
    Button btnYangcheon, btnYeongdeungpo, btnYongsan, btnSongpa, btnDobong, btnGangdong;
    Button btnEunpyeong, btnGangbuk, btnGuro, btnGangnam, btnGangseo, btnDongjak, btnDongdaemun;

    Button btnNext, btnHome;
    String address;
    Button lastClickedButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        btnSeongdong = (Button) findViewById(R.id.seongdong);
        btnMapo = (Button) findViewById(R.id.mapo);
        btnJongno = (Button) findViewById(R.id.jongno);
        btnGwanak = (Button) findViewById(R.id.gwanak);
        btnJungnang = (Button) findViewById(R.id.jungnang);
        btnGeumcheon = (Button) findViewById(R.id.geumcheon);

        btnGwangjin = (Button) findViewById(R.id.gwangjin);
        btnNowon = (Button) findViewById(R.id.nowon);
        btnJung = (Button) findViewById(R.id.jung);
        btnSeongbuk = (Button) findViewById(R.id.seongbuk);
        btnSeodaemun = (Button) findViewById(R.id.seodaemun);
        btnSeocho = (Button) findViewById(R.id.seocho);

        btnYangcheon = (Button) findViewById(R.id.yangcheon);
        btnYeongdeungpo = (Button) findViewById(R.id.yeongdeungpo);
        btnYongsan = (Button) findViewById(R.id.yongsan);
        btnSongpa = (Button) findViewById(R.id.songpa);
        btnDobong = (Button) findViewById(R.id.dobong);
        btnGangdong = (Button) findViewById(R.id.gangdong);

        btnEunpyeong = (Button) findViewById(R.id.eunpyeong);
        btnGangbuk = (Button) findViewById(R.id.gangbuk);
        btnGuro = (Button) findViewById(R.id.guro);
        btnGangnam = (Button) findViewById(R.id.gangnam);
        btnGangseo = (Button) findViewById(R.id.gangseo);
        btnDongjak = (Button) findViewById(R.id.dongjak);
        btnDongdaemun = (Button) findViewById(R.id.dongdaemun);

        btnNext = (Button) findViewById(R.id.go_next);
        btnHome = (Button) findViewById(R.id.go_home);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuActivity.this, CategoryActivity.class);
                intent.putExtra("User Address", address);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuActivity.this,MainActivity.class);
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
        if (v != btnNext && v != btnHome) address = clickedButton.getText().toString();
    }
}