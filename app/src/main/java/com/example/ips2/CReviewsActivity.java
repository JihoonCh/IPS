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

public class CReviewsActivity extends AppCompatActivity implements Adapter_Review.OnItemClickListener {

    Button btnCommunity, btnHome, btnMypage, btnWrite;

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_Review adapter_review;
    ArrayList<ReviewPost> list_rev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_reviews);

        recyclerView = findViewById(R.id.rv_rev);
        database = FirebaseDatabase.getInstance().getReference("Reviewposts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list_rev = new ArrayList<>();
        adapter_review = new Adapter_Review(this, list_rev, this);
        recyclerView.setAdapter(adapter_review);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_rev.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ReviewPost reviewpost = dataSnapshot.getValue(ReviewPost.class);
                    list_rev.add(reviewpost);
                }
                adapter_review.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CReviewsActivity", "Failed to read value.", error.toException());
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
                Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onItemClick(ReviewPost reviewPost) {
        String title = reviewPost.getTitle();
        String content = reviewPost.getContent();
        String postId = reviewPost.getPostId();

        // 새로운 액티비티로 title과 content, postId 전달
        Intent intent = new Intent(CReviewsActivity.this, OpenPostActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }
}