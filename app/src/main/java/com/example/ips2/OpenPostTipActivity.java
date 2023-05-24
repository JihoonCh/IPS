package com.example.ips2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ips2.R;
import com.example.ips2.ReviewPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenPostTipActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView;
    private EditText commentEditText;
    private Button commentButton;
    private LinearLayout commentsLayout;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_openpost);

        titleTextView = findViewById(R.id.postTitle);
        contentTextView = findViewById(R.id.postContent);
        commentEditText = findViewById(R.id.commentEditText);
        commentButton = findViewById(R.id.commentButton);
        commentsLayout = findViewById(R.id.commentsLayout);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // 인텐트에서 postId 가져오기
        postId = getIntent().getStringExtra("postId");

        if (postId == null) {
            // postId가 null인 경우 처리
            Toast.makeText(this, "cannot find posts that have comments", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // 게시물 데이터를 불러와서 출력
            loadPost();
            // 댓글 작성 버튼 클릭 리스너
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addComment();
                }
            });
            // 해당 게시물의 댓글을 불러와서 출력
            loadComments();
        }
    }

    private void loadPost() {
        DatabaseReference postRef = databaseReference.child("Tipposts").child(postId);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    TipPost tipPost = snapshot.getValue(TipPost.class);
                    if (tipPost != null) {
                        String title = tipPost.getTitle();
                        String content = tipPost.getContent();
                        titleTextView.setText(title);
                        contentTextView.setText(content);
                    }
                } else {
                    Toast.makeText(com.example.ips2.OpenPostTipActivity.this, "cannot find posts", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.ips2.OpenPostTipActivity.this, "Failed to load the post", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadComments() {
        DatabaseReference commentsRef = databaseReference.child("Comments").child(postId);
        commentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String commentText = dataSnapshot.getValue(String.class);
                    if (commentText != null) {
                        addCommentView(commentText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.ips2.OpenPostTipActivity.this, "Failed to load comments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCommentView(String commentText) {
        TextView commentTextView = new TextView(this);
        commentTextView.setText("> " + commentText);
        commentsLayout.addView(commentTextView);
    }

    private void addComment() {
        String commentText = commentEditText.getText().toString().trim();
        if (!commentText.isEmpty()) {
            DatabaseReference commentsRef = databaseReference.child("Comments").child(postId);
            String commentId = commentsRef.push().getKey();
            commentsRef.child(commentId).setValue(commentText);
            commentEditText.setText("");
            addCommentView(commentText);
        }
    }
}