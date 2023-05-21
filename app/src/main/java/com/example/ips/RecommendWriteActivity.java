package com.example.ips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RecommendWriteActivity extends AppCompatActivity {

    private EditText edtTitle, edtContent;
    private Button btnPost;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_main);

        // Firebase 데이터베이스의 "posts" 경로에 대한 참조를 가져옴
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContents);
        btnPost = findViewById(R.id.btnPost);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 제목과 내용
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString().trim();

                // 데이터베이스에 새로운 데이터를 업로드
                String postId = databaseReference.push().getKey();
                Post post = new Post(postId, title, content);
                databaseReference.child(postId).setValue(post);

                // 업로드 후 입력 필드를 초기화
                edtTitle.setText("");
                edtContent.setText("");
            }
        });
    }
}