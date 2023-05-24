package com.example.ips2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSeongdong, btnMapo, btnJongno, btnGwanak, btnJungnang, btnGeumcheon;
    Button btnGwangjin, btnNowon, btnJung, btnSeongbuk, btnSeodaemun, btnSeocho;
    Button btnYangcheon, btnYeongdeungpo, btnYongsan, btnSongpa, btnDobong, btnGangdong;
    Button btnEunpyeong, btnGangbuk, btnGuro, btnGangnam, btnGangseo, btnDongjak, btnDongdaemun;

    Button btnNext, btnHome;
    String address;
    Button lastClickedButton = null;

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

        btnSeongdong.setOnClickListener(this);
        btnMapo.setOnClickListener(this);
        btnJongno.setOnClickListener(this);
        btnGwanak.setOnClickListener(this);
        btnJungnang.setOnClickListener(this);
        btnGeumcheon.setOnClickListener(this);

        btnGwangjin.setOnClickListener(this);
        btnNowon.setOnClickListener(this);
        btnJung.setOnClickListener(this);
        btnSeongbuk.setOnClickListener(this);
        btnSeodaemun.setOnClickListener(this);
        btnSeocho.setOnClickListener(this);

        btnYangcheon.setOnClickListener(this);
        btnYeongdeungpo.setOnClickListener(this);
        btnYongsan.setOnClickListener(this);
        btnSongpa.setOnClickListener(this);
        btnDobong.setOnClickListener(this);
        btnGangdong.setOnClickListener(this);

        btnEunpyeong.setOnClickListener(this);
        btnGangbuk.setOnClickListener(this);
        btnGuro.setOnClickListener(this);
        btnGangnam.setOnClickListener(this);
        btnGangseo.setOnClickListener(this);
        btnDongjak.setOnClickListener(this);
        btnDongdaemun.setOnClickListener(this);

        btnNext = (Button) findViewById(R.id.go_next);
        btnHome = (Button) findViewById(R.id.go_home);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(address)) {
                    //구를 선택하지 않았을 때
                    Toast.makeText(GuActivity.this,
                            "Please select your location", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(GuActivity.this, CategoryActivity.class);
                intent.putExtra("User Address", address);
                startActivity(intent);
                finish();
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
        clickedButton.setBackgroundColor(Color.parseColor("#BB86FC"));
        lastClickedButton = clickedButton;
        if (v != btnNext && v != btnHome) address = clickedButton.getText().toString();
    }
}