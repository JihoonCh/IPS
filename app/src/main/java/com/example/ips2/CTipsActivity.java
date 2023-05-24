package com.example.ips2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import android.util.Log;
public class CTipsActivity extends AppCompatActivity {

    Button btnCommunity, btnHome, btnMypage, btnWrite;

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_Tips adapter_tips;
    ArrayList<TipPost> list_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_tips);

        recyclerView = findViewById(R.id.rv_tip);
        database = FirebaseDatabase.getInstance().getReference("Tipposts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list_tip = new ArrayList<>();
        adapter_tips = new Adapter_Tips(this,list_tip, new Adapter_Tips.OnItemClickListener() {
            @Override
            public void onItemClick(TipPost tipPost) {
                String title = tipPost.getTitle();
                String content = tipPost.getContent();
                String postId = tipPost.getPostId();

                // 새로운 액티비티로 title과 content 전달
                Intent intent = new Intent(CTipsActivity.this, OpenPostTipActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter_tips);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_tip.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TipPost tippost = dataSnapshot.getValue(TipPost.class);
                    list_tip.add(tippost);
                }
                adapter_tips.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // 버튼변수에 버튼객체 대입
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnCommunity = findViewById(R.id.btnCommunity);
        btnHome = findViewById(R.id.btnHome);
        btnMypage = findViewById(R.id.btnMyPage);

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
        // 글쓰기 버튼
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}