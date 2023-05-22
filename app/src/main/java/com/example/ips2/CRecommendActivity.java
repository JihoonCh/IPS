package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class CRecommendActivity extends AppCompatActivity {

    private ArrayList<RecommendPost> arrayList;
    private Adapter_recommend adapter_recommend;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    Button btnCommunity, btnHome, btnMypage, btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_recommend);

        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter_recommend = new Adapter_recommend(arrayList);
        recyclerView.setAdapter(adapter_recommend);

        // Add dummy data to the ArrayList
        arrayList.add(new RecommendPost("1", "Title 1", "Content 1"));
        arrayList.add(new RecommendPost("2", "Title 2", "Content 2"));
        arrayList.add(new RecommendPost("3", "Title 3", "Content 3"));

        // Refresh the RecyclerView
        adapter_recommend.notifyDataSetChanged();

        setTitle("메뉴판-음식추천앱");

        btnWrite = findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
/*
package com.example.ips2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class CRecommendActivity extends AppCompatActivity {

    private ArrayList<RecommendPost> arrayList;
    private Adapter_recommend adapter_recommend;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    Button btnCommunity, btnHome, btnMypage, btnWrite;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_recommend);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        //LayoutManager를 RecyclerView에 연결
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Adapter 객체를 RecyclerView에 연결
        arrayList = new ArrayList<>();
        adapter_recommend = new Adapter_recommend(arrayList);
        recyclerView.setAdapter(adapter_recommend);



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