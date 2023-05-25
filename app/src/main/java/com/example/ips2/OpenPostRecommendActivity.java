package com.example.ips2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenPostRecommendActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView;
    private EditText commentEditText;
    private Button commentButton;
    private LinearLayout commentsLayout;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private String postId;
    private ImageView addToScrapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_openpost);

        titleTextView = findViewById(R.id.postTitle);
        contentTextView = findViewById(R.id.postContent);
        commentEditText = findViewById(R.id.commentEditText);
        commentButton = findViewById(R.id.commentButton);
        commentsLayout = findViewById(R.id.commentsLayout);
        addToScrapImage = findViewById(R.id.addToScrapImage);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // 인텐트에서 postId 가져오기
        postId = getIntent().getStringExtra("postId");

        if (postId == null) {
            // postId가 null인 경우 처리
            Toast.makeText(this, "Cannot find post that have comments", Toast.LENGTH_SHORT).show();
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
            // 이미 scrap된 글인 경우 staricon으로 ImageView 설정
            if (isAlreadyScrapped()) {
                Drawable newDrawable = getResources().getDrawable(R.drawable.staricon);
                addToScrapImage.setImageDrawable(newDrawable);
            } else {
                Drawable newDrawable = getResources().getDrawable(R.drawable.staricon_before);
                addToScrapImage.setImageDrawable(newDrawable);
            }

            // 즐겨찾기 추가 버튼 클릭 리스너
            addToScrapImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isAlreadyScrapped()) {
                        addToScrapped();
                        // 변경할 이미지 리소스
                        Drawable newDrawable = getResources().getDrawable(R.drawable.staricon);
                        // ImageView의 srcCompat 변경
                        addToScrapImage.setImageDrawable(newDrawable);
                    } else {
                        removeFromScrapped();
                        // 변경할 이미지 리소스
                        Drawable newDrawable = getResources().getDrawable(R.drawable.staricon_before);
                        // ImageView의 srcCompat 변경
                        addToScrapImage.setImageDrawable(newDrawable);
                    }
                }
            });
        }
    }

    private boolean isAlreadyScrapped() {
        DatabaseReference scrappedRef = databaseReference.child("Scrap").child(currentUser.getUid());
        scrappedRef.orderByChild("postId").equalTo(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 이미 scrap된 글인 경우
                    // ImageView의 이미지를 staricon으로 변경
                    Drawable newDrawable = getResources().getDrawable(R.drawable.staricon);
                    addToScrapImage.setImageDrawable(newDrawable);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OpenPostRecommendActivity.this, "Failed to check if post is already scrapped", Toast.LENGTH_SHORT).show();
            }
        });

        return addToScrapImage.getDrawable() != null && addToScrapImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.staricon).getConstantState());
    }

    private void removeFromScrapped() {
        DatabaseReference scrappedRef = databaseReference.child("Scrap").child(currentUser.getUid());
        scrappedRef.orderByChild("postId").equalTo(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                    }
                    Toast.makeText(OpenPostRecommendActivity.this, "Post removed from scrap", Toast.LENGTH_SHORT).show();
                    Drawable newDrawable = getResources().getDrawable(R.drawable.staricon_before);
                    // ImageView의 srcCompat 변경
                    addToScrapImage.setImageDrawable(newDrawable);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OpenPostRecommendActivity.this, "Failed to remove post from scrap", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPost() {
        DatabaseReference postRef = databaseReference.child("Recommendposts").child(postId);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    RecommendPost recommendPost = snapshot.getValue(RecommendPost.class);
                    if (recommendPost != null) {
                        String title = recommendPost.getTitle();
                        String content = recommendPost.getContent();
                        titleTextView.setText(title);
                        contentTextView.setText(content);
                    }
                } else {
                    Toast.makeText(OpenPostRecommendActivity.this, "Cannot find the post", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OpenPostRecommendActivity.this, "Failed to load the post", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OpenPostRecommendActivity.this, "Failed to load comments", Toast.LENGTH_SHORT).show();
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

    private void addToScrapped() {
        DatabaseReference scrappedRef = databaseReference.child("Scrap").child(currentUser.getUid());

        DatabaseReference postRef = databaseReference.child("Recommendposts").child(postId);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Check if the post is already scrapped
                    scrappedRef.orderByChild("postId").equalTo(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(OpenPostRecommendActivity.this, "Post is already scrapped", Toast.LENGTH_SHORT).show();
                            } else {
                                RecommendPost recommendPost = snapshot.getValue(RecommendPost.class);
                                if (recommendPost != null) {
                                    String title = recommendPost.getTitle();
                                    String content = recommendPost.getContent();
                                    int whichPost = 2;

                                    ScrapPost scrapPost = new ScrapPost(postId, title, content, whichPost);
                                    scrapPost.setPostId(postId);
                                    scrapPost.setTitle(title);
                                    scrapPost.setContent(content);
                                    scrapPost.setWhichPost(whichPost);
                                    String scrapPostId = scrappedRef.push().getKey();
                                    scrappedRef.child(scrapPostId).setValue(scrapPost);
                                    Toast.makeText(OpenPostRecommendActivity.this, "Post scrapped", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(OpenPostRecommendActivity.this, "Failed to check if post is already scrapped", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(OpenPostRecommendActivity.this, "Cannot find the post", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OpenPostRecommendActivity.this, "Failed to load the post", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}