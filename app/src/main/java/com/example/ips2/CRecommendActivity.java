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
public class CRecommendActivity extends AppCompatActivity {

    Button btnCommunity, btnHome, btnMypage, btnWrite;

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_Recommend adapter_recommend;
    ArrayList<RecommendPost> list_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_recommend);

        recyclerView = findViewById(R.id.rv_rec);
        database = FirebaseDatabase.getInstance().getReference("Recommendposts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list_rec = new ArrayList<>();
        adapter_recommend = new Adapter_Recommend(this, list_rec, new Adapter_Recommend.OnItemClickListener() {
            @Override
            public void onItemClick(RecommendPost recommendPost) {
                String title = recommendPost.getTitle();
                String content = recommendPost.getContent();
                String postId = recommendPost.getPostId();

                // 새로운 액티비티로 title과 content 전달
                Intent intent = new Intent(CRecommendActivity.this, OpenPostRecommendActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter_recommend);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_rec.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RecommendPost recommendpost = dataSnapshot.getValue(RecommendPost.class);
                    list_rec.add(recommendpost);
                }
                adapter_recommend.notifyDataSetChanged();
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
                Intent intent = new Intent(getApplicationContext(), RecommendWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}