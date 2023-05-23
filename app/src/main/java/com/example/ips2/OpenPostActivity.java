package com.example.ips2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OpenPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_openpost);

        // 인텐트에서 title과 content 가져오기
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // title과 content를 출력할 TextView 찾기
        TextView titleTextView = findViewById(R.id.postTitle);
        TextView contentTextView = findViewById(R.id.postContent);

        // title과 content를 출력
        String titleText = title;
        titleTextView.setText(titleText);
        String contentText = content;
        contentTextView.setText(contentText);
    }
}