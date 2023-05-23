package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import android.util.Log;
public class CRecommendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_Recommend adapter_recommend;
    ArrayList<RecommendPost> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_recommend);

        recyclerView = findViewById(R.id.rv);
        database = FirebaseDatabase.getInstance().getReference("Recommendposts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter_recommend = new Adapter_Recommend(this,list);
        recyclerView.setAdapter(adapter_recommend);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    RecommendPost recommendpost = dataSnapshot.getValue(RecommendPost.class);
                    list.add(recommendpost);
                }
                adapter_recommend.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}


/*
        setTitle("메뉴판-음식추천앱");
        // 버튼변수에 버튼객체 대입
        btnWrite = (Button) findViewById(R.id.btnWrite);
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
*/
